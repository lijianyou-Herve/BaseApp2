package comte.example.herve.baseapp.base.view;

import com.uber.autodispose.AutoDisposeConverter;

import comte.example.herve.baseapp.base.presenter.BasePresenter;
import comte.example.herve.baseapp.utils.lifecycler.RxLifecycleUtils;

/**
 * Created by Herve on 2016/10/10.
 */

public abstract class MvpBaseActivity<P extends BasePresenter> extends BaseActivity {

    protected P mPresenter;

    @Override
    protected void baseActivityCreate() {
        super.baseActivityCreate();
        mPresenter = initPresenter();
        getLifecycle().addObserver(mPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract P initPresenter();

    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        return RxLifecycleUtils.bindLifecycle(this);
    }
}
