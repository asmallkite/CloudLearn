package com.kite.cloudlearn.ui.book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.kite.cloudlearn.R;
import com.kite.cloudlearn.base.BaseFragment;
import com.kite.cloudlearn.databinding.FragmentBookCustomBinding;

/**
 * Created by 10648 on 2017/5/18 0018.
 */

public class BookCustomFragment extends BaseFragment<FragmentBookCustomBinding> {

  private static final String TYPE = "param1";

  @Override public int setContent() {
    return R.layout.fragment_book_custom;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

  }
}
