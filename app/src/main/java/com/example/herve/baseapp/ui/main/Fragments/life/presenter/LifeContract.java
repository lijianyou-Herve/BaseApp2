package com.example.herve.baseapp.ui.main.Fragments.life.presenter;


import com.herve.library.commonlibrary.base.presenter.BasePresenter;
import com.herve.library.commonlibrary.base.presenter.BasePresenterView;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public interface LifeContract {


    interface Presenter extends BasePresenter {

        void loading();
    }

    interface PresenterView extends BasePresenterView {

        void getData();
    }
}
