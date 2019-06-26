package com.herve.library.commonlibrary.base.view;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import com.herve.library.commonlibrary.base.presenter.BasePresenter;
import com.herve.library.commonlibrary.utils.InstanceUtil;
import com.herve.library.commonlibrary.utils.lifecycler.RxLifecycleUtils;
import com.uber.autodispose.AutoDisposeConverter;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Herve on 2016/10/10.
 * 包内使用，不对外部开放使用
 * 在beta版本中（未来版本可以不在自己来实现LifecycleOwner接口,目前需要自己实现该接口）
 * 强制所有的Activity都需要继承这个类来实现
 */

public abstract class MvpBaseActivity<P extends BasePresenter> extends BaseActivity implements LifecycleOwner {

    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    protected P mPresenter;

    @Override
    protected void bindView() {
        super.bindView();
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.setLifecycleOwner(this);
            getLifecycle().addObserver(mPresenter);
        }
    }

    @CallSuper
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 创建Presenter 此处已重写 需要时重写即可
     *
     * @return
     */
    public P createPresenter() {
        if (this.getClass().getGenericSuperclass() instanceof ParameterizedType
                && ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
            Class mPresenterClass = (Class) ((ParameterizedType) (this.getClass().getGenericSuperclass()))
                    .getActualTypeArguments()[0];//获取Presenter的class
            return InstanceUtil.getInstance(mPresenterClass);
        }
        return null;
    }

    protected abstract P initPresenter();

    protected <T> AutoDisposeConverter<T> bindLifecycle(
            Lifecycle.Event untilEvent) {
        return RxLifecycleUtils.bindLifecycle(this, untilEvent);
    }

    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        return RxLifecycleUtils.bindLifecycle(this);
    }
}
