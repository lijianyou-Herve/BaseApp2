package com.example.herve.baseapp.ui.main.Fragments.life;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import com.example.herve.baseapp.R;
import com.example.herve.baseapp.base.view.MvpBaseFragment;
import com.example.herve.baseapp.ui.main.Fragments.life.adapter.LifeAdapter;
import com.example.herve.baseapp.ui.main.Fragments.life.presenter.LifeContract;
import com.example.herve.baseapp.ui.main.Fragments.life.presenter.LifePresenter;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public class LifeFragment extends MvpBaseFragment<LifeContract.Presenter> implements LifeContract.PresenterView {

  @BindView(R.id.recycle_view_life)
  RecyclerView recycleViewLife;

  public static LifeFragment newInstance() {
    LifeFragment fragment = new LifeFragment();
    return fragment;
  }

  public static LifeFragment newInstance(Bundle args) {
    LifeFragment fragment = new LifeFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void getData() {
    LifeAdapter lifeAdapter = new LifeAdapter(mContext);

    mPresenter.loading();

    RecyclerView.LayoutManager layoutManager =
        new GridLayoutManager(mContext, 4);
    recycleViewLife.setLayoutManager(layoutManager);
    recycleViewLife.setAdapter(lifeAdapter);

  }

  @Override
  protected LifeContract.Presenter initPresenter() {
    return new LifePresenter(this);
  }

  @Override
  protected int initLayoutId() {
    return R.layout.fragment_life_layout;
  }

  @Override
  protected void findViewById() {
    //if you using butterKnife you can doNothing
  }

  @Override
  protected void initView() {
    //you can set VIEW.setLayoutParams() at here;

  }

  @Override
  protected void initData() {
    mPresenter.loading();

  }

  @Override
  protected void initListener() {

  }

}
