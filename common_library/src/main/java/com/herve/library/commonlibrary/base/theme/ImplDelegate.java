package com.herve.library.commonlibrary.base.theme;

import android.content.res.Configuration;

import com.herve.library.commonlibrary.base.presenter.BasePresenterView;
import com.herve.library.httplibrary.ApiException;

import java.util.Locale;

/**
 * 用于Activity Fragment 界面交互
 */
public interface ImplDelegate extends BasePresenterView {

    public void onConfigurationChanged(Configuration newConfig);

    public void setOrientationPortrait(boolean mOrientationPortrait);

    public boolean isOrientationPortrait();

    /**
     * 重启Activity
     * 此方法会比 recreate() 效果更好
     */
    public void reload();

    public void changeLanguage(Locale language);

    public boolean isEqualsLanguage(Locale mLanguage, Locale locale);

    public void changeDayNightMode(boolean isNightMode);

    public void setStatusBarLightMode();

    public void setStatusBarDarkMode();

    public void showError(Throwable t);

    public void onApiException(ApiException t);
}
