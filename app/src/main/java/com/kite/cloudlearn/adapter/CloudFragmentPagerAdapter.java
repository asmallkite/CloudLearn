package com.kite.cloudlearn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by 10648 on 2017/5/26 0026.
 * 1. 没有使用匿名内部类
 * 2. 也没有把fragment 的创建放在adapter 内部
 * 出于  代码复用的考虑
 */

public class CloudFragmentPagerAdapter extends FragmentPagerAdapter {

  private List<Fragment> mFragments;
  private List<String> mTitles;

  public CloudFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
    super(fm);
    mFragments = fragments;
  }

  public CloudFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
    super(fm);
    mFragments = fragments;
    mTitles = titles;
  }

  @Override public Fragment getItem(int position) {
    return  mFragments.get(position);
  }

  @Override public int getCount() {
    return mFragments.size();
  }

  @Override public CharSequence getPageTitle(int position) {
    if (mTitles != null) {
      return mTitles.get(position);
    } else {
      return "";
    }
  }
}
