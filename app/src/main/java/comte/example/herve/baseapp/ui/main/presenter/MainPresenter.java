package comte.example.herve.baseapp.ui.main.presenter;

import android.util.Log;

import com.herve.library.commonlibrary.bean.LoginBean;
import com.herve.library.commonlibrary.utils.LogUtils;
import com.herve.library.httplibrary.Api;
import com.trello.rxlifecycle3.RxLifecycle;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.RxLifecycleAndroid;

import java.util.HashMap;

import comte.example.herve.baseapp.base.presenter.MvpBasePresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created           :Herve on 2016/10/23.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/23
 * @ projectName     :BaseApp
 * @ version
 */
public class MainPresenter extends MvpBasePresenter<MainConstant.PresenterView> implements MainConstant.Presenter {

    private static final String TAG = MainPresenter.class.getSimpleName();

    public MainPresenter(MainConstant.PresenterView mPresenterView) {
        super(mPresenterView);
    }

    @Override
    public void loadData() {
        LogUtils.logInit(true);

        HashMap<String, String> map = new HashMap<>();
        map.put("uid", "123456");
        map.put("mac", "mac");
        Api.getService_PHP()
                .login("login", map)
                .compose(mPresenterView.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mPresenterView.showDialog();
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        Log.i(TAG, "onNext: 成功");
                        mPresenterView.getDate();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: 失败");
                    }

                    @Override
                    public void onComplete() {
                        mPresenterView.dismissDialog();
                    }
                });

        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                Thread.sleep(8000);
                if (!emitter.isDisposed()) {
                    Log.i(TAG, "subscribe: 没有取消");
                    emitter.onNext(true);
                    emitter.onComplete();
                } else {
                    Log.i(TAG, "subscribe: 被取消");
                }
            }
        })
                .compose(RxLifecycle.bind(mPresenterView.lifecycle()))
                .compose(RxLifecycleAndroid.bindActivity(mPresenterView.lifecycle()))
                .compose(mPresenterView.bindUntilEvent(ActivityEvent.PAUSE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mPresenterView.showDialog();
                        Log.i(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                        Log.i(TAG, "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.i(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        mPresenterView.dismissDialog();
                        Log.i(TAG, "onComplete: ");
                    }
                });

    }

    @Override
    public void refreshData() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }
}
