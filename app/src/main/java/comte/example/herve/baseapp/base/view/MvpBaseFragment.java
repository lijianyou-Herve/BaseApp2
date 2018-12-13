package comte.example.herve.baseapp.base.view;


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

    @Override
    protected void baseCreate() {
        super.baseCreate();
        mPresenter = initPresenter();
    }

    protected abstract P initPresenter();

}
