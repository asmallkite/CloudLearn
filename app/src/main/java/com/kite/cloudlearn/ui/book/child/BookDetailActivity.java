package com.kite.cloudlearn.ui.book.child;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.Toast;
import com.kite.cloudlearn.R;
import com.kite.cloudlearn.base.BaseHeaderActivity;
import com.kite.cloudlearn.bean.book.BookDetailBean;
import com.kite.cloudlearn.bean.book.BooksBean;
import com.kite.cloudlearn.databinding.ActivityBookDetailBinding;
import com.kite.cloudlearn.databinding.HeaderBookDetailBinding;
import com.kite.cloudlearn.http.HttpClient;
import com.kite.cloudlearn.utils.CommonUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BookDetailActivity extends BaseHeaderActivity<HeaderBookDetailBinding, ActivityBookDetailBinding> {

  public final static String EXTRA_PARAM = "bookBean";
  HeaderBookDetailBinding mHeaderBookDetailBinding;
  private BooksBean mBooksBean;

  /**
   * 启动Activity
   * @param context BookFragment context
   * @param positionData BooksBeans
   * @param imageView 动画使用
   */
  public static void start(Activity context, BooksBean positionData, ImageView imageView) {
    Intent intent = new Intent(context, BookDetailActivity.class);
    intent.putExtra(EXTRA_PARAM, positionData);
    ActivityOptionsCompat options =
        ActivityOptionsCompat.makeSceneTransitionAnimation(context,
            imageView, CommonUtils.getString(R.string.transition_book_img));//与xml文件对应
    ActivityCompat.startActivity(context, intent, options.toBundle());
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_book_detail);
    //获取参数数据，Activity 之间传递数据
    if (getIntent() != null) {
      mBooksBean = (BooksBean) getIntent().getSerializableExtra(EXTRA_PARAM);
    }

    setMotion(setHeaderPicView(), false);

    setTitle(mBooksBean.getTitle());
    setSubTitle("作者：" + mBooksBean.getAuthor());

    bindingHeaderView.setBooksBean(mBooksBean);
    bindingHeaderView.executePendingBindings();

    loadBookDetail();

  }

  private void loadBookDetail() {
    Disposable get = HttpClient.Builder.getDoubanService().getBookDetail(mBooksBean.getId())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<BookDetailBean>() {
          @Override public void onNext(@NonNull BookDetailBean bookDetailBean) {
            bindingContentView.setBookDetailBean(bookDetailBean);
            bindingContentView.executePendingBindings();
          }

          @Override public void onError(@NonNull Throwable e) {
            showError();
          }

          @Override public void onComplete() {
            showContentView();
          }
        });
    addDisposable(get);
  }

  @Override protected void setTitleClickMore() {
    super.setTitleClickMore();
    Toast.makeText(this, "click more", Toast.LENGTH_SHORT).show();
  }

  @Override protected int setHeaderLayout() {
    mHeaderBookDetailBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_book_detail, null, false);
    return R.layout.header_book_detail;
  }

  @Override protected ImageView setHeaderPicView() {
    return bindingHeaderView.ivOnePhoto;
  }

  @Override protected String setHeaderImgUrl() {
    if (mBooksBean == null) {
      return "";
    }
    return mBooksBean.getImages().getMedium();
  }


  @Override protected ImageView setHeaderImageView() {
    return mHeaderBookDetailBinding.imgItemBg;
  }
}
