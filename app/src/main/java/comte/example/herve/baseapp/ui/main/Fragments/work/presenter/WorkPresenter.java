package comte.example.herve.baseapp.ui.main.Fragments.work.presenter;


import comte.example.herve.baseapp.base.presenter.MvpBasePresenter;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public class WorkPresenter extends MvpBasePresenter<WorkContract.PresenterView> implements WorkContract.Presenter {

    public WorkPresenter(WorkContract.PresenterView mPresenter) {
        super(mPresenter);
    }

    private void initData() {
        loading();
    }

    @Override
    public void loading() {
        mPresenterView.showDialog();
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }
}
