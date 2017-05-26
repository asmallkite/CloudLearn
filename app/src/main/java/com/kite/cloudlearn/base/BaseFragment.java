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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.kite.cloudlearn.R;
import rx.subscriptions.CompositeSubscription;

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
  private CompositeSubscription mCompositeSubscription;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View ll = inflater.inflate(R.layout.fragment_base, null);
    bindingView = DataBindingUtil.inflate(getActivity().getLayoutInflater(), setContent(), null, false);

    return ll;

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

  public abstract int setContent();

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


}
