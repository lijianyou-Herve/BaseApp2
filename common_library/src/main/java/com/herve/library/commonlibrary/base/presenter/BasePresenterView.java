package com.herve.library.commonlibrary.base.presenter;


/**
 * Created by Herve on 2016/10/10.
 */

public interface BasePresenterView {

    void showProgressDialog();

    void showProgressDialog(CharSequence message);

    void dismissProgressDialog();
}
