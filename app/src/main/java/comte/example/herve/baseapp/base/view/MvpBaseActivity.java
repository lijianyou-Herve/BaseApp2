package comte.example.herve.baseapp.base.view;

import comte.example.herve.baseapp.base.presenter.BasePresenter;

/**
 * Created by Herve on 2016/10/10.
 */

public abstract class MvpBaseActivity<P extends BasePresenter> extends BaseActivity {

    protected P mPresenter;

    @Override
    protected void baseActivityCreate() {
        super.baseActivityCreate();
        mPresenter = initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract P initPresenter();
}
