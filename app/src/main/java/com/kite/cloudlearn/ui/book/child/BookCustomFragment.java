package com.kite.cloudlearn.ui.book.child;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.kite.cloudlearn.R;
import com.kite.cloudlearn.adapter.BookAdapter;
import com.kite.cloudlearn.base.BaseFragment;
import com.kite.cloudlearn.bean.book.BookBean;
import com.kite.cloudlearn.databinding.FragmentBookCustomBinding;
import com.kite.cloudlearn.http.HttpClient;
import com.kite.cloudlearn.utils.CommonUtils;
import com.kite.cloudlearn.utils.DebugUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 10648 on 2017/5/18 0018.
 */

public class BookCustomFragment extends BaseFragment<FragmentBookCustomBinding> {

  private static final String TYPE = "param1";
  private String mType = "文学";
  private boolean mIsPrepared;
  private boolean mNoData = true;

  private int mStart = 0;
  private int mCount = 18;

  private BookAdapter mBookAdapter;
  private GridLayoutManager mLayoutManager;

  public static BookCustomFragment newInstance(String key) {
    BookCustomFragment instance = new BookCustomFragment();
    Bundle bundle = new Bundle();
    bundle.putString(TYPE, key);
    instance.setArguments(bundle);
    return instance;
  }


  @Override public int setContent() {
    return R.layout.fragment_book_custom;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (getArguments() != null) {
      mType = getArguments().getString(TYPE);
    }
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

    scrollRecyclerView();

    mIsPrepared = true;

    loadData();

  }

  @Override protected void loadData() {
    if (!mIsPrepared || !mIsVisible || !mNoData) {
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
              mNoData = false;
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


  public void scrollRecyclerView() {
    bindingView.xrvBook.addOnScrollListener(new RecyclerView.OnScrollListener() {

      int lastVisibleItem;

      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
          lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
          if (mBookAdapter == null) {
            return;
          }
          if (lastVisibleItem + 1 == mLayoutManager.getItemCount()
              && mBookAdapter.getStatus() != BookAdapter.LOAD_MORE) {
            mBookAdapter.updateLoadStatus(BookAdapter.LOAD_MORE);
            new Handler().postDelayed(new Runnable() {
              @Override public void run() {
                mStart += mCount;
                loadCustomData();
              }
            }, 0);
          }
        }
      }

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
      }
    });
  }
}
