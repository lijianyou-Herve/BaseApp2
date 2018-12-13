package comte.example.herve.baseapp.ui.main.presenter;

import com.trello.rxlifecycle3.android.ActivityEvent;

import comte.example.herve.baseapp.base.presenter.BasePresenter;
import comte.example.herve.baseapp.base.presenter.BasePresenterView;

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

    interface PresenterView extends BasePresenterView<ActivityEvent> {

        void getDate();

    }
}
