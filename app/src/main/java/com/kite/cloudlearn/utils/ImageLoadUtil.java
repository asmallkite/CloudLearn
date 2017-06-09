package com.kite.cloudlearn.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.kite.cloudlearn.R;

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
