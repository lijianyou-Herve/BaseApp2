package comte.example.herve.baseapp.ui.main.presenter;

import android.view.View;

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
        mPresenter.setProgressVisibility(View.VISIBLE);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.success();
                mPresenter.setProgressVisibility(View.GONE);
            }
        },200);


    }

    @Override
    public void refreshData() {

    }
}
