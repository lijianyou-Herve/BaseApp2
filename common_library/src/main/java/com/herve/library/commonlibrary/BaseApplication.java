package com.herve.library.commonlibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.herve.library.commonlibrary.bean.Result;
import com.herve.library.commonlibrary.config.Config;
import com.herve.library.commonlibrary.utils.ActivityManager;
import com.herve.library.commonlibrary.utils.LogUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class BaseApplication extends Application {

  private static BaseApplication instance;

  //static 代码段可以防止内存泄露
  static {
    //设置全局的Header构建器
    SmartRefreshLayout.setDefaultRefreshHeaderCreator(
        new DefaultRefreshHeaderCreator() {
          @Override
          public RefreshHeader createRefreshHeader(Context context,
              RefreshLayout layout) {
            //layout.setPrimaryColorsId(android.R.color.transparent);//全局设置主题颜色
            return new ClassicsHeader(context).setSpinnerStyle(
                SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
          }
        });
    //设置全局的Footer构建器
    SmartRefreshLayout.setDefaultRefreshFooterCreator(
        new DefaultRefreshFooterCreator() {
          @Override
          public RefreshFooter createRefreshFooter(Context context,
              RefreshLayout layout) {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setSpinnerStyle(
                SpinnerStyle.Translate);
          }
        });
  }

  public static BaseApplication getInstance() {
    return instance;
  }

  private RefWatcher mRefWatcher;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;

    //初始化配置
    config();

  }

  private void config() {
    Config.MClASS = Result.class;//Json解析

    //内存泄漏检测
    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return;
    }
    mRefWatcher =
        BuildConfig.DEBUG ? LeakCanary.install(this) : RefWatcher.DISABLED;
    // mRefWatcher = RefWatcher.DISABLED;
    //屏幕适配
    //AutoSizeConfig.getInstance().setDesignWidthInDp(360).setDesignHeightInDp(640);
    Logger.addLogAdapter(new AndroidLogAdapter());//logger
    registerActivityLifecycle();

  }

  public RefWatcher getRefWatcher() {
    return mRefWatcher;
  }

  /**
   * 全局管理Activity
   */
  private void registerActivityLifecycle() {
    registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
      @Override
      public void onActivityCreated(Activity activity,
          Bundle savedInstanceState) {
        ActivityManager.getInstance().add(activity);//加入管理栈

      }

      @Override
      public void onActivityStarted(Activity activity) {

      }

      @Override
      public void onActivityResumed(Activity activity) {

      }

      @Override
      public void onActivityPaused(Activity activity) {

      }

      @Override
      public void onActivityStopped(Activity activity) {

      }

      @Override
      public void onActivitySaveInstanceState(Activity activity,
          Bundle outState) {

      }

      @Override
      public void onActivityDestroyed(Activity activity) {
        ActivityManager.getInstance().remove(activity);//移除管理栈
        mRefWatcher.watch(activity);
      }
    });
  }

}
