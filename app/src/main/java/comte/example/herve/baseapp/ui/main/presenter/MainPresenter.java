package comte.example.herve.baseapp.ui.main.presenter;

import android.util.Log;

import androidx.lifecycle.Lifecycle;

import com.herve.library.commonlibrary.bean.LoginResult;
import com.herve.library.httplibrary.Api;

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

    HashMap<String, String> map = new HashMap<>();
    map.put("uid", "123456");
    map.put("mac", "mac");

    Api.getService_PHP()
        .login("login", map)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .as(bindLifecycle())//绑定生命周期
//        .as(bindLifecycle(Lifecycle.Event.ON_PAUSE))//用来声明在生命特定周期下中止计划
        .subscribe(new Observer<LoginResult>() {
          @Override
          public void onSubscribe(Disposable d) {
            mPresenterView.showProgressDialog();
          }

          @Override
          public void onNext(LoginResult loginResult) {
            if (loginResult.getCode() == 0) {
              Log.i(TAG, "onNext: 成功");
            }
            mPresenterView.getDate();
          }

          @Override
          public void onError(Throwable e) {
            Log.i(TAG, "onError: 失败");
            mPresenterView.dismissProgressDialog();
          }

          @Override
          public void onComplete() {
            mPresenterView.dismissProgressDialog();
          }
        });

    Observable.create(new ObservableOnSubscribe<Long>() {
      @Override
      public void subscribe(ObservableEmitter<Long> emitter) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
          if (!emitter.isDisposed()) {
            emitter.onError(e);
          }
        }
        emitter.onNext(100L);
        emitter.onComplete();
        Log.i(TAG, "subscribe: " + Thread.currentThread().getName());
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
//        .as(bindLifecycle())//AutoDispose
        .as(bindLifecycle(Lifecycle.Event.ON_PAUSE))//用来声明在生命特定周期下中止计划
        .subscribe(new Observer<Long>() {
          @Override
          public void onSubscribe(Disposable d) {
            mPresenterView.showProgressDialog();
            Log.i(TAG, "onSubscribe: " + Thread.currentThread().getName());
          }

          @Override
          public void onNext(Long aLong) {
            Log.i(TAG, "onNext: " + Thread.currentThread().getName());

          }

          @Override
          public void onError(Throwable e) {
            Log.i(TAG, "onError: ");
            mPresenterView.dismissProgressDialog();
          }

          @Override
          public void onComplete() {
            mPresenterView.dismissProgressDialog();
            Log.i(TAG, "onComplete: " + Thread.currentThread().getName());
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
