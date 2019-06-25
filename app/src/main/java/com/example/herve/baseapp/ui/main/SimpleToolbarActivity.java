package com.example.herve.baseapp.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.herve.library.commonlibrary.utils.BaseSPManager;
import com.mirkowu.basetoolbar.BaseToolbar;

import java.util.Locale;

import butterknife.BindView;
import com.example.herve.baseapp.R;
import com.example.herve.baseapp.base.presenter.BasePresenter;
import com.example.herve.baseapp.base.view.ToolbarActivity;

public class SimpleToolbarActivity extends ToolbarActivity {

  @BindView(R.id.btn_change_style)
  Button mBtnChangeStyle;
  @BindView(R.id.btn_change_font)
  Button mBtnChangeFont;

  public static void launch(Context context) {
    Intent intent = new Intent(context, SimpleToolbarActivity.class);
    context.startActivity(intent);
  }

  @Nullable
  @Override
  protected BaseToolbar.Builder setToolbar(
      @NonNull BaseToolbar.Builder builder) {
    builder.setTitle(getString(R.string.setting_tittle));
    return builder;
  }

  @Override
  protected BasePresenter initPresenter() {
    return null;
  }

  @Override
  protected int initLayoutId() {
    return R.layout.activity_simple_toolbar;
  }

  @Override
  protected void initView() {

  }

  @Override
  protected void initData() {

  }

  @Override
  protected void initListener() {
    mBtnChangeStyle.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //切换日夜模式
        BaseSPManager.setNightMode(!BaseSPManager.isNightMode());
        reload();
      }
    });

    mBtnChangeFont.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Locale language = BaseSPManager.getLanguage();
        if (isEqualsLanguage(Locale.ENGLISH, language)) {
          changeLanguage(Locale.SIMPLIFIED_CHINESE);
        } else {
          changeLanguage(Locale.ENGLISH);
        }
        reload();
      }
    });
  }

}
