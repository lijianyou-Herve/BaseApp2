package com.example.herve.baseapp.ui.main.Fragments.life.presenter;


import com.herve.library.commonlibrary.base.presenter.MvpBasePresenter;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public class LifePresenter extends MvpBasePresenter<LifeContract.PresenterView> implements LifeContract.Presenter {

    public LifePresenter(LifeContract.PresenterView mPresenter) {
        super(mPresenter);
    }

    @Override
    public void loading() {
        mPresenterView.showProgressDialog();

        //模拟操作,随便就能写的展示用法，没有真实json,会崩溃
        //        String jsonDta = "[]";
        //        ArrayList<SquareBean> data =
        //                GsonUtils.fromJsonArray(jsonDta, SquareBean.class);
        //
        //        Observable.fromIterable(data)
        //                .map(new Function<SquareBean, SquareBean>() {
        //                    @Override
        //                    public SquareBean apply(SquareBean squareBean) throws Exception {
        //                        squareBean.setTitle(squareBean.getTitle() + squareBean.getResId());
        //                        squareBean.setResId(R.drawable.ic_donut_small_black_24dp);
        //                        return squareBean;
        //                    }
        //                })
        //                .as(bindLifecycle())
        //                .subscribe(new Observer<SquareBean>() {
        //                    @Override
        //                    public void onSubscribe(Disposable d) {
        //
        //                    }
        //
        //                    @Override
        //                    public void onNext(SquareBean squareBean) {
        //
        //                    }
        //
        //                    @Override
        //                    public void onError(Throwable e) {
        //
        //                    }
        //
        //                    @Override
        //                    public void onComplete() {
        //
        //                    }
        //                });
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }
}
