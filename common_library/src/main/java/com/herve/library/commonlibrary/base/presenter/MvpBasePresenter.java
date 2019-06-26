package com.herve.library.commonlibrary.base.presenter;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.herve.library.commonlibrary.utils.lifecycler.RxLifecycleUtils;
import com.uber.autodispose.AutoDisposeConverter;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public class MvpBasePresenter<V extends BasePresenterView> implements BasePresenter {

    protected V mPresenterView;
    private LifecycleOwner lifecycleOwner;

    public MvpBasePresenter(V mPresenterView) {
        this.mPresenterView = mPresenterView;
    }

    protected <T> AutoDisposeConverter<T> bindLifecycle(Lifecycle.Event untilEvent) {
        if (null == lifecycleOwner)
            throw new NullPointerException("lifecycleOwner == null");
        return RxLifecycleUtils.bindLifecycle(lifecycleOwner, untilEvent);
    }

    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        if (null == lifecycleOwner)
            throw new NullPointerException("lifecycleOwner == null");
        return RxLifecycleUtils.bindLifecycle(lifecycleOwner);
    }

    @Override
    public void showDialog() {
        mPresenterView.showProgressDialog();
    }

    @Override
    public void dismissDialog() {
        mPresenterView.dismissProgressDialog();
    }

    @Override
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    protected Context getContext() {
        return null;
    }
}
