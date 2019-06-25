package com.example.herve.baseapp.base.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.herve.library.commonlibrary.utils.BaseSPManager;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.mirkowu.statusbarutil.StatusBarUtil;

import butterknife.ButterKnife;
import com.example.herve.baseapp.R;
import com.example.herve.baseapp.base.presenter.BasePresenter;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public abstract class ToolbarActivity<P extends BasePresenter> extends MvpBaseActivity<P> {
  private BaseToolbar mBaseToolbar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void bindView() {
    /*** 这里可以对Toolbar进行统一的预设置 */
    BaseToolbar.Builder builder
        = new BaseToolbar.Builder(mActivity)
        .setBackButton(R.mipmap.back)//统一设置返回键
        //    .setStatusBarColor(ContextUtil.getColor(R.color.colorPrimary))//统一设置颜色
        .setBackgroundColor(
            ContextCompat.getColor(mActivity, R.color.colorPrimary))
        .setSubTextColor(Color.WHITE)
        .setTitleTextColor(Color.WHITE);

    builder = setToolbar(builder);
    if (builder != null) {
      mBaseToolbar = builder.build();
    }
    if (mBaseToolbar != null) {
      //添加Toolbar
      LinearLayout layout = new LinearLayout(mActivity);
      LinearLayout.LayoutParams params =
          new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
      layout.setLayoutParams(params);
      layout.setOrientation(LinearLayout.VERTICAL);
      layout.addView(mBaseToolbar);
      View mView = getLayoutInflater().inflate(initLayoutId(), layout, false);
      layout.addView(mView);
      setContentView(layout);
      //将toolbar设置为actionbar
      setSupportActionBar(mBaseToolbar);
    } else {
      setContentView(initLayoutId());
    }

    //设置沉浸式透明状态栏
    //StatusBarUtil.setTransparent(this);
    StatusBarUtil.setStatusBarColor(mActivity,
        ContextCompat.getColor(mActivity, R.color.colorPrimary));

    //ButterKnife
    mUnBinder = ButterKnife.bind(this);
  }


  public void setStatusBarLightMode() {
    if (!BaseSPManager.isNightMode()) {
      if (StatusBarUtil.setStatusBarLightModeWithNoSupport(mActivity,
          true)) {
        if (getToolbar() != null) getToolbar().hideStatusBar();
      }
    }
  }

  public BaseToolbar getToolbar() {
    return mBaseToolbar;
  }


  public void showToolbar() {
    if (mBaseToolbar != null) mBaseToolbar.setVisibility(View.VISIBLE);
  }

  public void hideToolbar() {
    if (mBaseToolbar != null) mBaseToolbar.setVisibility(View.GONE);
  }

  /**
   * 不需要toolbar的 可以不用管
   * 可以再次自定义toolbar
   *
   * @return
   */
  @Nullable
  protected abstract BaseToolbar.Builder setToolbar(
      @NonNull BaseToolbar.Builder builder);
}
