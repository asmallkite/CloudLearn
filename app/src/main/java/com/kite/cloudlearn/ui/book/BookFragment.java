package com.kite.cloudlearn.ui.book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.kite.cloudlearn.R;
import com.kite.cloudlearn.adapter.CloudFragmentPagerAdapter;
import com.kite.cloudlearn.base.BaseFragment;
import com.kite.cloudlearn.databinding.FragmentBookBinding;
import com.kite.cloudlearn.test.BlankFragment;
import com.kite.cloudlearn.ui.book.child.BookCustomFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10648 on 2017/6/4 0004.
 */

public class BookFragment extends BaseFragment<FragmentBookBinding> {

  private List<String> mTitleList = new ArrayList<>(3);
  private List<Fragment> mFragments = new ArrayList<>(3);

  private static final String TAB1 = "文学";
  private static final String TAB2 = "文化";
  private static final String TAB3 = "生活";



  @Override public int setContent() {
    return R.layout.fragment_book;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    showLoading();
    initFragmentList();

    CloudFragmentPagerAdapter adapter = new CloudFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
    bindingView.vpBook.setAdapter(adapter);

    bindingView.vpBook.setOffscreenPageLimit(2);
    adapter.notifyDataSetChanged();
    bindingView.tabBook.setTabMode(TabLayout.MODE_FIXED);
    bindingView.tabBook.setupWithViewPager(bindingView.vpBook);
    showContentView();
  }

  private void initFragmentList() {
    mTitleList.add(TAB1);
    mTitleList.add(TAB2);
    mTitleList.add(TAB3);
    mFragments.add(BookCustomFragment.newInstance(TAB1));
    mFragments.add(BookCustomFragment.newInstance(TAB2));
    mFragments.add(BookCustomFragment.newInstance(TAB3));
  }
}
