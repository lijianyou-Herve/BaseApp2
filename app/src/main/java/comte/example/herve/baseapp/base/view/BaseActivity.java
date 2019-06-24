package comte.example.herve.baseapp.base.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonParseException;
import com.herve.library.commonlibrary.utils.BaseSPManager;
import com.herve.library.commonlibrary.utils.ToastUtil;
import com.herve.library.httplibrary.ApiException;
import com.mirkowu.statusbarutil.StatusBarUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import comte.example.herve.baseapp.R;
import comte.example.herve.baseapp.base.presenter.BasePresenterView;
import comte.example.herve.baseapp.base.theme.BaseDelegate;
import comte.example.herve.baseapp.base.theme.ImplDelegate;

/**
 * Created           :Herve on 2016/9/27.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/9/27
 * @ projectName     :OurSchool
 * @ version
 */

abstract class BaseActivity extends AppCompatActivity implements ImplDelegate, BasePresenterView {

  protected AppCompatActivity mActivity;
  protected Unbinder mUnBinder;
  private String TAG = getClass().getSimpleName();

  protected abstract int initLayoutId();

  protected abstract void initView();

  protected abstract void initData();

  protected abstract void initListener();

  protected BaseDelegate mBaseDelegate;

  @NonNull
  public BaseDelegate getBaseDelegate() {
    if (mBaseDelegate == null) {
      mBaseDelegate = new BaseDelegate(this);
    }
    return mBaseDelegate;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //设置委托
    final BaseDelegate delegate = getBaseDelegate();
    delegate.onCreate(savedInstanceState);

    setContentView(initLayoutId());
    mUnBinder = ButterKnife.bind(this);
    mActivity = this;
    baseActivityCreate();

    initView();

    initData();

    initListener();
  }

  /**
   *
   */
  protected void baseActivityCreate() {

  }

  /**
   * 权限提示对话框
   */
  public void showPermissionDialog() {
    new AlertDialog.Builder(this)
        .setTitle(R.string.base_prompt_message)
        .setMessage(R.string.base_permission_lack)
        .setNegativeButton(R.string.base_cancel,
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
              }
            })
        .setPositiveButton(R.string.base_ok,
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
              }
            }).show();
  }

  /**
   * 启动当前应用设置页面
   */
  public void startAppSettings() {
    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    intent.setData(Uri.parse("package:" + getPackageName()));
    startActivity(intent);
  }

  @Override
  protected void onResume() {
    getBaseDelegate().onResume();
    super.onResume();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(getBaseDelegate().attachBaseContext(newBase));
  }

  /**
   * 这个可以视情况 重写 (当横竖屏等配置发生改变时)
   *
   * @param newConfig
   */
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    getBaseDelegate().onConfigurationChanged(newConfig);
  }

  /**
   * 设置横屏竖屏
   *
   * @param mOrientationPortrait true 竖屏 false 横屏
   */
  public void setOrientationPortrait(boolean mOrientationPortrait) {
    getBaseDelegate().setOrientationPortrait(mOrientationPortrait);
  }

  public boolean isOrientationPortrait() {
    return getBaseDelegate().isOrientationPortrait();
  }

  @Override
  protected void onDestroy() {
    if (mUnBinder != null) mUnBinder.unbind();
    super.onDestroy();
  }

  @Override
  public void reload() {
    getBaseDelegate().reload();
  }

  @Override
  public void changeLanguage(Locale language) {
    getBaseDelegate().changeLanguage(language, true);
  }

  @Override
  public boolean isEqualsLanguage(Locale mLanguage, Locale locale) {
    return false;
  }

  @Override
  public void changeDayNightMode(boolean isNightMode) {
    getBaseDelegate().changeDayNightMode(isNightMode);
  }

  @Override
  public void setStatusBarLightMode() {
    if (!BaseSPManager.isNightMode()) {
      StatusBarUtil.setStatusBarLightModeWithNoSupport(this, true);
    }
  }

  @Override
  public void setStatusBarDarkMode() {
    if (!BaseSPManager.isNightMode()) {
      if (StatusBarUtil.setStatusBarDarkMode(this) == 0) {//不支持 亮色 模式
//                //恢复过来时的 处理
//                StatusBarUtil.setTransparent(this);
      }
    }
  }

  /**
   * 显示加载框
   */
  @Override
  public void showProgressDialog() {
    getBaseDelegate().showProgressDialog();
  }

  /**
   * 显示加载框（带文字）
   */
  @Override
  public void showProgressDialog(CharSequence message) {
    getBaseDelegate().showProgressDialog(message);
  }

  /**
   * 隐藏加载框
   */
  @Override
  public void dismissProgressDialog() {
    getBaseDelegate().hideLoading();
  }

  /**
   * 全局简单异常处理
   *
   * @param t
   */
  @Override
  public void showError(Throwable t) {
    if (t instanceof ConnectException) {
      ToastUtil.s(getString(R.string.base_connect_failed));
    } else if (t instanceof UnknownHostException) {
      ToastUtil.s(getString(R.string.base_request_serve_failed));
    } else if (t instanceof SocketTimeoutException) {
      ToastUtil.s(getString(R.string.base_socket_timeout));
    } else if (t instanceof JsonParseException) {
      ToastUtil.s(getString(R.string.base_parse_failed));
      t.printStackTrace();
    } else if (t instanceof ApiException) {
      //通用的Api异常处理
      onApiException((ApiException) t);
    } else {
      getBaseDelegate().showError(t);
    }
  }

  /**
   * 全局的详细异常处理 根据项目情况重写
   *
   * @param t
   */
  @Override
  public void onApiException(ApiException t) {
    getBaseDelegate().showError(t);
  }
}
