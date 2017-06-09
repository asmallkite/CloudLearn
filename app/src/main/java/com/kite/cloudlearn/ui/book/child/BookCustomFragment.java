package com.kite.cloudlearn.ui.book.child;

import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import com.kite.cloudlearn.R;
import com.kite.cloudlearn.adapter.BookAdapter;
import com.kite.cloudlearn.base.BaseFragment;
import com.kite.cloudlearn.bean.book.BookBean;
import com.kite.cloudlearn.databinding.FragmentBookCustomBinding;
import com.kite.cloudlearn.http.HttpClient;
import com.kite.cloudlearn.utils.CommonUtils;
import com.kite.cloudlearn.utils.DebugUtil;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscription;

/**
 * Created by 10648 on 2017/5/18 0018.
 */

public class BookCustomFragment extends BaseFragment<FragmentBookCustomBinding> {

  private static final String TYPE = "param1";
  private String mType = "文学";
  private boolean mIsPrepared;
  private boolean mIsFirst = true;

  private int mStart = 0;
  private int mCount = 18;

  private BookAdapter mBookAdapter;
  private GridLayoutManager mLayoutManager;

  @Override public int setContent() {
    return R.layout.fragment_book_custom;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    showContentView();
    bindingView.srlBook.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
    bindingView.srlBook.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        bindingView.srlBook.postDelayed(new Runnable() {
          @Override public void run() {
            mStart = 0;
            loadCustomData();
          }
        }, 1000);
      }
    });

    mLayoutManager = new GridLayoutManager(getActivity(), 3);
    bindingView.xrvBook.setLayoutManager(mLayoutManager);

    mIsPrepared = true;

    loadData();

  }

  @Override protected void loadData() {
    if (!mIsPrepared || !mIsVisible || !mIsFirst) {
      return;
    }
    bindingView.srlBook.setRefreshing(true);
    bindingView.srlBook.postDelayed(new Runnable() {
      @Override public void run() {
        loadCustomData();
      }
    }, 500);
  }

  private void loadCustomData() {

    Disposable disposable = HttpClient.Builder.getDoubanService().getBook(mType, mStart, mCount)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<BookBean>() {
          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
            DebugUtil.error("error" + e.toString());
            showContentView();
            if (bindingView.srlBook.isRefreshing()) {
              bindingView.srlBook.setRefreshing(false);
            }
            if (mStart == 0) {
              //showError();
            }
          }

          @Override public void onComplete() {
            showContentView();
            if (bindingView.srlBook.isRefreshing()) {
              bindingView.srlBook.setRefreshing(false);
            }
          }

          @Override
          public void onNext(BookBean bookBean) {
            DebugUtil.debug("onNext");
            if (mStart == 0) {
              if (bookBean != null && bookBean.getBooks() != null && bookBean.getBooks().size() > 0) {

                if (mBookAdapter == null) {
                  mBookAdapter = new BookAdapter(getActivity());
                }
                mBookAdapter.setList(bookBean.getBooks());
                mBookAdapter.notifyDataSetChanged();
                bindingView.xrvBook.setAdapter(mBookAdapter);

              }
              mIsFirst = false;
            } else {
              mBookAdapter.addAll(bookBean.getBooks());
              mBookAdapter.notifyDataSetChanged();
            }
            if (mBookAdapter != null) {
              mBookAdapter.updateLoadStatus(BookAdapter.LOAD_PULL_TO);
            }
          }
        });

    addDisposable(disposable);
  }

  @Override protected void onRefresh() {
    bindingView.srlBook.setRefreshing(true);
    loadCustomData();
  }
}
