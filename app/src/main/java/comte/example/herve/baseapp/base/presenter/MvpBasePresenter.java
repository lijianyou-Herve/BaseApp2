package comte.example.herve.baseapp.base.presenter;

import android.os.Handler;
import android.os.Looper;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public  abstract class MvpBasePresenter<V extends BasePresenterView> implements BasePresenter {

    protected V mPresenterView;

    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private CompositeDisposable mCompositeDisposable;

    protected Handler mHandler;

    public MvpBasePresenter(V mPresenterView) {
        this.mPresenterView = mPresenterView;

        mHandler = new Handler(Looper.getMainLooper());

    }

    @Override
    public void detach() {

    }

    /**
     * 将Disposable添加
     *
     * @param subscription
     */
    @Override
    public void addDisposable(Disposable subscription) {
        //csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    @Override
    public void unDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    @Override
    public void showDialog() {
        mPresenterView.showDialog();
    }

    @Override
    public void dismissDialog() {
        mPresenterView.dismissDialog();
    }

}
