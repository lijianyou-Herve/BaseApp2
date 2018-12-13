package comte.example.herve.baseapp.ui.main.Fragments.life.presenter;


import com.trello.rxlifecycle3.android.FragmentEvent;

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
public interface LifeContract {


    interface Presenter extends BasePresenter {

        void loading();
    }

    interface PresenterView extends BasePresenterView<FragmentEvent> {

        void getData();
    }
}
