package com.kite.cloudlearn;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.kite.cloudlearn.adapter.CloudFragmentPagerAdapter;
import com.kite.cloudlearn.databinding.ActivityMainBinding;
import com.kite.cloudlearn.test.BlankFragment;
import com.kite.cloudlearn.utils.CommonUtils;
import com.kite.cloudlearn.view.statusbar.StatusBarUtil;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {


  private FrameLayout llTitleMenu;
  private Toolbar toolbar;
  private NavigationView navView;
  private DrawerLayout drawer;
  private ViewPager vpContent;

  private ActivityMainBinding mBinding;
  private ImageView llTitleGank;
  private ImageView llTitleOne;
  private ImageView llTitleDou;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    initStatusView();

    initId();

    initListener();

    List<Fragment> fragments = new ArrayList<>(3);
    fragments.add(new BlankFragment());
    fragments.add(new BlankFragment());
    fragments.add(new BlankFragment());
    CloudFragmentPagerAdapter fragmentPagerAdapter = new CloudFragmentPagerAdapter(getSupportFragmentManager(), fragments);
    vpContent.setAdapter(fragmentPagerAdapter);
    vpContent.setOffscreenPageLimit(2);
    vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override public void onPageSelected(int position) {
        switch (position) {
          case 0:
            llTitleGank.setSelected(true);
            llTitleOne.setSelected(false);
            llTitleDou.setSelected(false);
            break;
          case 1:
            llTitleOne.setSelected(true);
            llTitleGank.setSelected(false);
            llTitleDou.setSelected(false);
            break;
          case 2:
            llTitleDou.setSelected(true);
            llTitleOne.setSelected(false);
            llTitleGank.setSelected(false);
            break;
        }

      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });


    StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, drawer,
        CommonUtils.getColor(R.color.colorTheme));
  }

  private void initListener() {
    llTitleGank.setOnClickListener(this);
    llTitleOne.setOnClickListener(this);
    llTitleDou.setOnClickListener(this);
  }

  private void initId() {
    drawer = mBinding.drawerLayout;
    navView = mBinding.navView;
    toolbar = mBinding.include.toolbar;
    vpContent = mBinding.include.vpContent;
    llTitleGank = mBinding.include.ivTitleGank;
    llTitleOne = mBinding.include.ivTitleOne;
    llTitleDou = mBinding.include.ivTitleDou;
    llTitleGank.setSelected(true); //设置第一个 选中状态

    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    navView.setNavigationItemSelectedListener(this);
  }

  private void initStatusView() {
    ViewGroup.LayoutParams layoutParams = mBinding.include.viewStatus.getLayoutParams();
    layoutParams.height = StatusBarUtil.getStatusBarHeight(this);
    mBinding.include.viewStatus.setLayoutParams(layoutParams);
  }

  @Override public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }


  @SuppressWarnings("StatementWithEmptyBody") @Override public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera) {
      // Handle the camera action
    } else if (id == R.id.nav_gallery) {

    } else if (id == R.id.nav_slideshow) {

    } else if (id == R.id.nav_manage) {

    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_title_gank:
        vpContent.setCurrentItem(0, true);
        break;
      case R.id.iv_title_one:
        vpContent.setCurrentItem(1, true);
        break;

      case R.id.iv_title_dou:
        vpContent.setCurrentItem(2, true);
        break;
      default:
        break;
    }

  }
}
