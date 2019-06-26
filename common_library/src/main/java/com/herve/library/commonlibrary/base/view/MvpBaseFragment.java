package com.herve.library.commonlibrary.base.view;


import com.herve.library.commonlibrary.base.presenter.BasePresenter;
import com.herve.library.commonlibrary.utils.InstanceUtil;

import java.lang.reflect.ParameterizedType;


/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public abstract class MvpBaseFragment<P extends BasePresenter> extends BaseFragment {

    protected P mPresenter;

    @Override
    protected void baseCreate() {
        super.baseCreate();
        mPresenter = initPresenter();
        //        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.setLifecycleOwner(this);
            getLifecycle().addObserver(mPresenter);
        }
    }

    protected abstract P initPresenter();

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
}
