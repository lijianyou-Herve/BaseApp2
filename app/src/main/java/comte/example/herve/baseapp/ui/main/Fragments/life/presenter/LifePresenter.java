package comte.example.herve.baseapp.ui.main.Fragments.life.presenter;


import android.view.View;

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
public class LifePresenter extends MvpBasePresenter<LifeContract.PresenterView> implements LifeContract.Presenter {


    public LifePresenter(LifeContract.PresenterView mPresenter) {
        super(mPresenter);
    }


    @Override
    public void loading() {
        mPresenter.setProgressVisibility(View.VISIBLE);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.setProgressVisibility(View.GONE);
                mPresenter.success();
            }
        }, 1000);

    }
}
