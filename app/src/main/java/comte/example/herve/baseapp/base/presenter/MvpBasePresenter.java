package comte.example.herve.baseapp.base.presenter;

import android.content.Context;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public abstract class MvpBasePresenter<V extends BasePresenterView> {

    protected V mPresenterView;

    public MvpBasePresenter(V mPresenterView) {
        this.mPresenterView = mPresenterView;
        if (!(mPresenterView instanceof Context)) {
            throw new IllegalArgumentException("PresenterView must instanceof Context");
        }
    }

    protected Context getContext() {
        return (Context) mPresenterView;
    }
}
