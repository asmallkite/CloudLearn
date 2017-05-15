package com.kite.cloudlearn.app;

import android.app.Application;

/**
 * Created by 10648 on 2017/5/15 0015.
 */

public class CloudReaderApplication extends Application{


  private static CloudReaderApplication sCloudReaderApplication;

  public static CloudReaderApplication getInstance() {
    return sCloudReaderApplication;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    sCloudReaderApplication = this;
  }
}
