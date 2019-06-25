package com.example.herve.baseapp.ui.main.presenter;


import com.example.herve.baseapp.base.presenter.BasePresenter;
import com.example.herve.baseapp.base.presenter.BasePresenterView;

/**
 * Created           :Herve on 2016/10/23.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/23
 * @ projectName     :BaseApp
 * @ version
 */
public interface MainConstant {

    interface Presenter extends BasePresenter {

        void loadData();

        void refreshData();

    }

    interface PresenterView extends BasePresenterView {

        void getDate();

    }
}
