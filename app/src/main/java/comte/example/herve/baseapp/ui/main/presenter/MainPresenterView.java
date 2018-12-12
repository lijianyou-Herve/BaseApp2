package comte.example.herve.baseapp.ui.main.presenter;

import comte.example.herve.baseapp.base.presenter.MvpBasePresenter;

/**
 * Created           :Herve on 2016/10/23.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/23
 * @ projectName     :BaseApp
 * @ version
 */
public class MainPresenterView extends MvpBasePresenter<MainConstant.PresenterView> implements MainConstant.Presenter {

    public MainPresenterView(MainConstant.PresenterView mPresenter) {
        super(mPresenter);
    }

    @Override
    public void loadData() {
        mPresenterView.showDialog();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenterView.success();
                mPresenterView.dismissDialog();
            }
        },200);


    }

    @Override
    public void refreshData() {

    }
}
