package com.kite.cloudlearn.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kite.cloudlearn.R;
import com.kite.cloudlearn.databinding.BaseHeaderTitleBarBinding;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by 10648 on 2017/6/10 0010.
 */

public abstract class BaseHeaderActivity<HV extends ViewDataBinding, SV extends ViewDataBinding> extends AppCompatActivity {

  // toolbar 布局
  protected BaseHeaderTitleBarBinding bindingTitleView;
  // 内容布局头部
  protected HV bindingHeaderView;
  // 内容布局view
  protected SV bindingContentView;

  private LinearLayout llProgressBar;
  private View refresh;
  private AnimationDrawable mAnimationDrawable;

  private CompositeDisposable mCompositeDisposable;

  @Override public void setContentView(@LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
    View ll = getLayoutInflater().inflate(R.layout.activity_header_base, null);

    // 内容：作者简介 摘要 目录等 部分
    bindingContentView = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);

    bindingHeaderView = DataBindingUtil.inflate(getLayoutInflater(), setHeaderLayout(), null, false);

    bindingTitleView = DataBindingUtil.inflate(getLayoutInflater(), R.layout.base_header_title_bar, null, false);

    // 在activity_header_base 的浮动布局加入toolbar
    RelativeLayout mTitleContainer = (RelativeLayout) ll.findViewById(R.id.title_container);
    mTitleContainer.addView(bindingTitleView.getRoot());
    // 加入 高斯头布局
    RelativeLayout mHeaderContainer = (RelativeLayout) ll.findViewById(R.id.header_container);
    mHeaderContainer.addView(bindingHeaderView.getRoot());
    // 加入主内容布局
    RelativeLayout mContainer = (RelativeLayout) ll.findViewById(R.id.container);
    mContainer.addView(bindingContentView.getRoot());
    getWindow().setContentView(ll);

    llProgressBar = (LinearLayout) ll.findViewById(R.id.ll_progress_bar);
    refresh = ll.findViewById(R.id.ll_error_refresh);

    setToolBar();

    setMotion(setHeaderPicView(), true);

    //initSlideShapeTheme(setHeaderImgUrl(), setHeaderImageView());

    ImageView img = (ImageView) ll.findViewById(R.id.img_progress);

    // 加载动画
    mAnimationDrawable = (AnimationDrawable) img.getDrawable();
    // 默认进入页面就开启动画
    if (!mAnimationDrawable.isRunning()) {
      mAnimationDrawable.start();
    }

    bindingContentView.getRoot().setVisibility(View.GONE);
  }










//***********************关于toolbar***********************************************

  /**
   * 标题
   */
  public void setTitle(CharSequence text) {
    bindingTitleView.tbBaseTitle.setTitle(text);
  }

  /**
   *  副标题
   */
  protected void setSubTitle(CharSequence text) {
    bindingTitleView.tbBaseTitle.setSubtitle(text);
  }

  /**
   *  toolbar 单击"更多信息"
   */
  protected void setTitleClickMore() {

  }

  private void setToolBar() {
    setSupportActionBar(bindingTitleView.tbBaseTitle);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayShowTitleEnabled(false);
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
    }
    bindingTitleView.tbBaseTitle.setTitleTextAppearance(this, R.style.ToolBar_Title);
    bindingTitleView.tbBaseTitle.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitle);
    bindingTitleView.tbBaseTitle.inflateMenu(R.menu.base_header_menu);
    bindingTitleView.tbBaseTitle.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.actionbar_more));
    bindingTitleView.tbBaseTitle.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        onBackPressed();
      }
    });
    bindingTitleView.tbBaseTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
          case R.id.actionbar_more:
            setTitleClickMore();
            break;
        }
        return false;
      }
    });
  }

  //*******************************************************
  //******************* Header layout    ******************

  /**
   * 设置自定义 Shared Element切换动画
   * 默认不开启曲线路径切换动画，
   * 开启需要重写setHeaderPicView()，和调用此方法并将isShow值设为true
   *
   * @param imageView 共享的图片
   * @param isShow    是否显示曲线动画
   */
  protected void setMotion(ImageView imageView, boolean isShow) {
    if (!isShow) {
      return;
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      ChangeBounds changeBounds = new ChangeBounds();
      //定义ArcMotion
      ArcMotion arcMotion = new ArcMotion();
      arcMotion.setMinimumHorizontalAngle(50f);
      arcMotion.setMinimumVerticalAngle(50f);
      //Interpolator 被用来修饰动画效果，定义动画的变化率
      // ，可以使存在的动画效果accelerated(加速)，decelerated(减速),repeated(重复),bounced(弹跳)等。
      Interpolator interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);
      changeBounds.setPathMotion(arcMotion);
      changeBounds.setInterpolator(interpolator);
      changeBounds.addTarget(imageView);
      getWindow().setSharedElementEnterTransition(changeBounds);
      getWindow().setSharedElementReturnTransition(changeBounds);
    }
  }

  /**
   *   高斯透明布局
   * @return int
   */
  protected abstract int setHeaderLayout();

  /**
   * 设置头部header布局 左侧的图片(需要设置曲线路径切换动画时重写)
   */
  protected ImageView setHeaderPicView() {
    return new ImageView(this);
  }

  /**
   * 高斯模糊 背景
   */
  //private void setImgHeaderBg(String imgUrl, ImageView imageView) {
  //  if (!TextUtils.isEmpty(imgUrl)) {
  //    Glide.with(this).load(imgUrl)
  //        //.error(R.drawable.stackblur_default)
  //        .bitmapTransform(new BlurTransformation(this, 14, 5))
  //        //.listener(new RequestListener<String, GlideDrawable>() {
  //        //  @Override
  //        //  public boolean onException(Exception e, String model, Target<GlideDrawable> target,
  //        //      boolean isFirstResource) {
  //        //    return false;
  //        //  }
  //        //
  //        //  @Override public boolean onResourceReady(GlideDrawable resource, String model,
  //        //      Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
  //        //    bindingTitleView.tbBaseTitle.setBackgroundColor(Color.TRANSPARENT);
  //        //    //bindingTitleView.ivBaseTitlebarBg.setImageAlpha(0);
  //        //    bindingTitleView.ivBaseTitlebarBg.setVisibility(View.VISIBLE);
  //        //    return false;
  //        //  }
  //        //})
  //        .into(bindingTitleView.ivBaseTitlebarBg);
  //  }
  //
  //}

  /**
   * *** 初始化滑动渐变 ******
   *
   * @param imgUrl    header头部的高斯背景imageUrl
   * @param mHeaderBg header头部高斯背景ImageView控件
   */
  //protected void initSlideShapeTheme(String imgUrl, ImageView mHeaderBg) {
  //  setImgHeaderBg(imgUrl, mHeaderBg);
  //}

  /**
   * 设置头部header高斯背景 imgUrl
   */
  protected abstract String setHeaderImgUrl();

  /**
   * 设置头部header布局 高斯背景ImageView控件
   */
  protected abstract ImageView setHeaderImageView();













  @Override public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.base_header_menu, menu);
    return true;
  }

  protected void showContentView() {
    if (llProgressBar.getVisibility() != View.GONE) {
      llProgressBar.setVisibility(View.GONE);
    }
    // 停止动画
    if (mAnimationDrawable.isRunning()) {
      mAnimationDrawable.stop();
    }
    if (refresh.getVisibility() != View.GONE) {
      refresh.setVisibility(View.GONE);
    }
    if (bindingContentView.getRoot().getVisibility() != View.VISIBLE) {
      bindingContentView.getRoot().setVisibility(View.VISIBLE);
    }
  }

  protected void showError() {
    if (llProgressBar.getVisibility() != View.GONE) {
      llProgressBar.setVisibility(View.GONE);
    }
    // 停止动画
    if (mAnimationDrawable.isRunning()) {
      mAnimationDrawable.stop();
    }
    if (refresh.getVisibility() != View.VISIBLE) {
      refresh.setVisibility(View.VISIBLE);
    }
    if (bindingContentView.getRoot().getVisibility() != View.GONE) {
      bindingContentView.getRoot().setVisibility(View.GONE);
    }
  }

  public void addDisposable(Disposable disposable) {
    if (mCompositeDisposable == null) {
      mCompositeDisposable = new CompositeDisposable();
    }
    mCompositeDisposable.add(disposable);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mCompositeDisposable.clear();
  }
}
