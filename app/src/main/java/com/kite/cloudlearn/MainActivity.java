package com.kite.cloudlearn;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.kite.cloudlearn.databinding.ActivityMainBinding;
import com.kite.cloudlearn.utils.CommonUtils;
import com.kite.cloudlearn.view.statusbar.StatusBarUtil;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


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

    StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, drawer,
        CommonUtils.getColor(R.color.colorTheme));
  }

  private void initId() {
    drawer = mBinding.drawerLayout;
    navView = mBinding.navView;
    toolbar = mBinding.include.toolbar;
    vpContent = mBinding.include.vpContent;
    llTitleGank = mBinding.include.ivTitleGank;
    llTitleOne = mBinding.include.ivTitleOne;
    llTitleDou = mBinding.include.ivTitleDou;

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
}
