package comte.example.herve.baseapp.base.ui;


import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import comte.example.herve.baseapp.base.presenter.BasePresenter;


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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mPresenter = initPresenter();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract P initPresenter();
}
