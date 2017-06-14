package com.kite.cloudlearn.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.kite.cloudlearn.R;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by 10648 on 2017/5/18 0018.
 */

public abstract class BaseFragment<SV extends ViewDataBinding> extends Fragment {

  // 布局view
  protected SV bindingView;
  // fragment是否显示了
  protected boolean mIsVisible = false;
  // 加载中
  private LinearLayout mLlProgressBar;
  // 加载失败
  private LinearLayout mRefresh;
  // 内容布局
  protected RelativeLayout mContainer;
  // 动画
  private AnimationDrawable mAnimationDrawable;
  private CompositeDisposable mCompositeDisposable;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View ll = inflater.inflate(R.layout.fragment_base, null);
    bindingView = DataBindingUtil.inflate(getActivity().getLayoutInflater(), setContent(), null, false);
    mContainer = (RelativeLayout) ll.findViewById(R.id.container);
    mContainer.addView(bindingView.getRoot());
    return ll;

  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mLlProgressBar = getView(R.id.ll_progress_bar);
    ImageView imageView = getView(R.id.img_progress);
    //  加载动画
    mAnimationDrawable = (AnimationDrawable) imageView.getDrawable();
    //默认进入页面就开启动画
    if (!mAnimationDrawable.isRunning()) {
      mAnimationDrawable.start();
    }
    mRefresh = getView(R.id.ll_error_refresh);
    mRefresh.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        showLoading();
        onRefresh();
      }
    });
    bindingView.getRoot().setVisibility(View.GONE);
  }

  @Override public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (getUserVisibleHint()) {
      mIsVisible = true;
      onVisible();
    } else {
      mIsVisible = false;
      onInVisible();
    }
  }

  protected void onInVisible(){

  }

  protected void onVisible() {
    loadData();
  }

  protected void loadData() {

  }

  protected void onRefresh() {

  }

  public abstract int setContent();


  protected void showLoading() {
    if (mLlProgressBar.getVisibility() != View.VISIBLE) {
      mLlProgressBar.setVisibility(View.VISIBLE);
    }
    if (!mAnimationDrawable.isRunning()) {
      mAnimationDrawable.start();
    }
    if (bindingView.getRoot().getVisibility() != View.GONE){
      bindingView.getRoot().setVisibility(View.GONE);
    }
    if (mRefresh.getVisibility() != View.GONE) {
      mRefresh.setVisibility(View.GONE);
    }
  }

  protected <T extends View> T getView(int id) {
    return (T) getView().findViewById(id);
  }

  /**
   * 加载完成的状态
   */
  protected void showContentView() {
    if (mLlProgressBar.getVisibility() != View.GONE) {
      mLlProgressBar.setVisibility(View.GONE);
    }
    // 停止动画
    if (mAnimationDrawable.isRunning()) {
      mAnimationDrawable.stop();
    }
    if (mRefresh.getVisibility() != View.GONE) {
      mRefresh.setVisibility(View.GONE);
    }
    if (bindingView.getRoot().getVisibility() != View.VISIBLE) {
      bindingView.getRoot().setVisibility(View.VISIBLE);
    }
  }

  /**
   * 加载失败点击重新加载的状态
   */
  protected void showError() {
    if (mLlProgressBar.getVisibility() != View.GONE) {
      mLlProgressBar.setVisibility(View.GONE);
    }
    // 停止动画
    if (mAnimationDrawable.isRunning()) {
      mAnimationDrawable.stop();
    }
    if (mRefresh.getVisibility() != View.VISIBLE) {
      mRefresh.setVisibility(View.VISIBLE);
    }
    if (bindingView.getRoot().getVisibility() != View.GONE) {
      bindingView.getRoot().setVisibility(View.GONE);
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
