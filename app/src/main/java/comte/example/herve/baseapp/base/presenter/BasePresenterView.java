package comte.example.herve.baseapp.base.presenter;

import com.trello.rxlifecycle3.LifecycleProvider;

/**
 * Created by Herve on 2016/10/10.
 */

public interface BasePresenterView<E> extends LifecycleProvider<E> {

    void showDialog();

    void dismissDialog();
}
