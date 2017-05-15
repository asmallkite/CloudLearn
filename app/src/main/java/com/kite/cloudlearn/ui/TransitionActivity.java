package com.kite.cloudlearn.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.bumptech.glide.Glide;
import com.kite.cloudlearn.MainActivity;
import com.kite.cloudlearn.R;
import com.kite.cloudlearn.app.ConstantsImageUrl;
import com.kite.cloudlearn.databinding.ActivityTransitionBinding;
import com.kite.cloudlearn.utils.CommonUtils;
import java.util.Random;

public class TransitionActivity extends AppCompatActivity {

  private ActivityTransitionBinding mBinding;
  private boolean mIsIn;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_transition);

    int i = new Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.length);
    mBinding.ivDefultPic.setImageDrawable(CommonUtils.getDrawable(R.drawable.img_transition_default));

    Glide.with(this)
        .load(ConstantsImageUrl.TRANSITION_URLS[i])
        .placeholder(R.drawable.img_transition_default)
        .error(R.drawable.img_transition_default)
        .into(mBinding.ivPic);

    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        mBinding.ivDefultPic.setVisibility(View.GONE);
      }
    }, 1500);

    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        toMainActivity();
      }
    }, 3500);

    mBinding.tvJump.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        toMainActivity();
      }
    });

  }

  private void toMainActivity() {
    if (mIsIn) {
      return;
    }
    startActivity(new Intent(this, MainActivity.class));
    overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
    mIsIn = true;
    finish();
  }
}
