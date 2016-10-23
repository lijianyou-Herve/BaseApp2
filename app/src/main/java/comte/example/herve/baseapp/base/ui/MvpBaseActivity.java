package comte.example.herve.baseapp.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import comte.example.herve.baseapp.base.presenter.BasePresenter;

/**
 * Created by Herve on 2016/10/10.
 */

public abstract class MvpBaseActivity<P extends BasePresenter> extends BaseActivity {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();

        super.onCreate(savedInstanceState);
    }

    protected abstract P initPresenter();
}
