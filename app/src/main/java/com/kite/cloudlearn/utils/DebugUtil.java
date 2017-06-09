package com.kite.cloudlearn.utils;

import android.util.Log;

/**
 * Created by 10648 on 2017/6/4 0004.
 */

public class DebugUtil {
  public static final String TAG = "CloudLearn";
  private static final boolean DEBUG = true;

  public static void debug(String tag, String msg) {
    if (DEBUG) {
      Log.d(tag, "debug:" + msg);
    }
  }

  public static void debug(String msg) {
    if (DEBUG) {
      Log.d(TAG, "debug: " + msg);
    }
  }

  public static void error(String tag, String error) {
    if (DEBUG) {
      Log.e(tag, "error: " + error);
    }
  }

  public static void error(String error) {
    if (DEBUG) {
      Log.e(TAG, "error: "+ error );
    }
  }
}
