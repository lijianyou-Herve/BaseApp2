package comte.example.herve.baseapp.ui.main.Fragments.work.presenter;


import comte.example.herve.baseapp.base.presenter.BasePresenter;
import comte.example.herve.baseapp.base.presenter.BasePresenterView;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public interface WorkContract {


    interface Presenter extends BasePresenter {

        void loading();
    }

    interface PresenterView extends BasePresenterView {

    }
}
