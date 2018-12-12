package comte.example.herve.baseapp.base.presenter;

import io.reactivex.disposables.Disposable;

/**
 * Created by Herve on 2016/10/10.
 */

public interface BasePresenter {

    void detach();

    //将网络请求的每一个disposable添加进入CompositeDisposable，再退出时候一并注销
    void addDisposable(Disposable subscription);

    //注销所有请求
    void unDisposable();

    void  showDialog();

    void dismissDialog();
}
