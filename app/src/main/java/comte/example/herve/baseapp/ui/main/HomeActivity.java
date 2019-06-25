package comte.example.herve.baseapp.ui.main;

import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import comte.example.herve.baseapp.R;
import comte.example.herve.baseapp.base.view.BaseFragment;
import comte.example.herve.baseapp.base.view.MvpBaseActivity;
import comte.example.herve.baseapp.ui.main.Fragments.life.LifeFragment;
import comte.example.herve.baseapp.ui.main.adapter.FragmentsAdapter;
import comte.example.herve.baseapp.ui.main.presenter.MainConstant;
import comte.example.herve.baseapp.ui.main.presenter.MainPresenter;
import comte.example.herve.baseapp.ui.webView.WebViewActivity;

public class HomeActivity extends MvpBaseActivity<MainConstant.Presenter> implements MainConstant.PresenterView {

  @BindView(R.id.tab_layout)
  TabLayout tabLayout;
  @BindView(R.id.vp_home)
  ViewPager vpHome;
  @BindView(R.id.bottom_view)
  BottomNavigationView bottomView;
  @BindView(R.id.activity_home)
  ConstraintLayout activityHome;

  private FragmentsAdapter fragmentsAdapter;
  private String TAG = getClass().getSimpleName();

  @Override
  protected int initLayoutId() {
    return R.layout.activity_home;
  }

  @Override
  protected void initView() {
  }

  @Override
  protected void initData() {
    mPresenter.loadData();
  }

  @Override
  protected void initListener() {

    bottomView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Log.i(TAG, "onNavigationItemSelected:getOrder= " + item.getOrder());
            switch (item.getItemId()) {
              case R.id.tab_01:

                vpHome.setCurrentItem(0);
                SimpleToolbarActivity.launch(mActivity);
                break;
              case R.id.tab_02:
                WebViewActivity.launch(mActivity, "网页", "http://www.baidu.com");
                vpHome.setCurrentItem(1);
                break;
              case R.id.tab_03:
                vpHome.setCurrentItem(2);
                break;
              default:
                Log.w(TAG,
                    "onNavigationItemSelected: you have not get the MenuItem id");
                break;
            }
            return false;
          }
        });
  }

  @Override
  protected MainConstant.Presenter initPresenter() {
    return new MainPresenter(this);
  }

  @Override
  public void getDate() {
    fragmentsAdapter =
        new FragmentsAdapter(getSupportFragmentManager(), mActivity);

    ArrayList<BaseFragment> data = new ArrayList<>();

    for (int i = 0; i < 3; i++) {
      BaseFragment baseFragment;
      if (i == 0) {
        baseFragment = new LifeFragment();
        baseFragment.setTittle("生活");

      } else {
        baseFragment = new LifeFragment();
        baseFragment.setTittle("工作");
      }
      data.add(baseFragment);
    }

    fragmentsAdapter.setData(data);

    vpHome.setOffscreenPageLimit(data.size());

    vpHome.setAdapter(fragmentsAdapter);

    tabLayout.setupWithViewPager(vpHome);
  }

}
