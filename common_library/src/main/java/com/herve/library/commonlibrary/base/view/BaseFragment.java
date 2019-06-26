package com.herve.library.commonlibrary.base.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.herve.library.commonlibrary.base.presenter.BasePresenterView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements BasePresenterView {
    protected BaseActivity mContext;
    protected View mRootView;
    protected Unbinder mUnbinder;
    protected String tittle;

    protected boolean mIsViewInitiated;
    protected boolean mIsVisibleToUser;
    protected boolean mIsDataInitiated;

    protected abstract int initLayoutId();

    protected abstract void findViewById();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        initFetchData();
    }

    protected void initFetchData() {
        if (mIsVisibleToUser && mIsViewInitiated && !mIsDataInitiated) {
            initData();
            initListener();
            mIsDataInitiated = true;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (BaseActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsViewInitiated = true;
        initFetchData();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(initLayoutId(), null, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        baseCreate();
        findViewById();
        initView();
        return mRootView;
    }

    protected void baseCreate() {

    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    @Override
    public void showProgressDialog() {
        mContext.showProgressDialog();
    }

    @Override
    public void showProgressDialog(CharSequence message) {
        mContext.showProgressDialog();

    }

    @Override
    public void dismissProgressDialog() {
        mContext.showProgressDialog();
    }

}