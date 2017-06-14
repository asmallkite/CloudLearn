package com.kite.cloudlearn.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.kite.cloudlearn.R;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by 10648 on 2017/6/8 0008.
 */

public class ImageLoadUtil {

  /**
   * 书籍列表 展示图片
   */
  @BindingAdapter("showBookImg")
  public static void showBookImg(ImageView imageView, String url) {
    Glide.with(imageView.getContext())
        .load(url)
        .crossFade(500)
        .override((int) CommonUtils.getDimens(R.dimen.book_detail_width), (int) CommonUtils.getDimens(R.dimen.book_detail_height))
        .placeholder(getDefaultPic(2))
        .error(getDefaultPic(2))
        .into(imageView);
  }

  /**
   * 高斯模糊背景
   * @param imageView
   * @param url
   */
  @BindingAdapter("showGaussian")
  public static void showGaussian(ImageView imageView, String url) {
    Glide.with(imageView.getContext())
        .load(url)
        .error(R.drawable.stackblur_default)
        .placeholder(R.drawable.stackblur_default)
        .crossFade(500)
        .bitmapTransform(new BlurTransformation(imageView.getContext(), 23, 4))
        .into(imageView);
  }

  private static int getDefaultPic(int type) {
    switch (type) {
      case 0:// 电影
        return R.drawable.img_default_movie;
      case 1:// 妹子
        return R.drawable.img_default_meizi;
      case 2:// 书籍
        return R.drawable.img_default_book;
    }
    return R.drawable.img_default_meizi;
  }
}
