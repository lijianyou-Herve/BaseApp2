package comte.example.herve.baseapp.base.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();//在presenter中解绑释放view
            mPresenter = null;
        }
    }

    protected abstract P initPresenter();
}
