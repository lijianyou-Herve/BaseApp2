package com.herve.library.commonlibrary.base.presenter;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by Herve on 2016/10/10.
 */

public interface BasePresenter extends LifecycleObserver {

    void setLifecycleOwner(LifecycleOwner lifecycleOwner);

    void showDialog();

    void dismissDialog();
}
