package com.example.herve.baseapp.base.presenter;

import android.content.Context;

import com.herve.library.commonlibrary.utils.lifecycler.RxLifecycleUtils;
import com.uber.autodispose.AutoDisposeConverter;

import org.greenrobot.greendao.annotation.NotNull;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public abstract class MvpBasePresenter<V extends BasePresenterView> implements IPresenter {

    protected V mPresenterView;
    private LifecycleOwner lifecycleOwner;

    public MvpBasePresenter(V mPresenterView) {
        this.mPresenterView = mPresenterView;
        if (!(mPresenterView instanceof Context)) {
            throw new IllegalArgumentException("PresenterView must instanceof Context");
        }
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
    public void onLifecycleChanged(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public void onCreate(@NotNull LifecycleOwner owner) {
    }

    @Override
    public void onStart(LifecycleOwner owner) {
    }

    @Override
    public void onResume(LifecycleOwner owner) {

    }

    @Override
    public void onPause(LifecycleOwner owner) {

    }

    @Override
    public void onStop(LifecycleOwner owner) {

    }

    public void onDestroy(@NotNull LifecycleOwner owner) {

    }

    protected Context getContext() {
        return (Context) mPresenterView;
    }
}
