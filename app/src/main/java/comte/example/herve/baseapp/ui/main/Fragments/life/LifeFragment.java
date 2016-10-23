package comte.example.herve.baseapp.ui.main.Fragments.life;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import comte.example.herve.baseapp.R;
import comte.example.herve.baseapp.base.ui.MvpBaseFragment;
import comte.example.herve.baseapp.bean.SquareBean;
import comte.example.herve.baseapp.ui.main.Fragments.life.adapter.LifeAdapter;
import comte.example.herve.baseapp.ui.main.Fragments.life.presenter.LifeContract;
import comte.example.herve.baseapp.ui.main.Fragments.life.presenter.LifePresenter;
import comte.example.herve.baseapp.utils.fastjson.FastJsonParser;
import comte.example.herve.baseapp.utils.string.StringUtils;
import rx.Observable;
import rx.functions.Action1;


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
    @BindView(R.id.progress)
    ProgressBar progress;


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
    public void setProgressVisibility(int visibility) {
        progress.setVisibility(visibility);

    }

    @Override
    public void success() {
        LifeAdapter lifeAdapter = new LifeAdapter(mContext);


        String jsonDta = StringUtils.getJson(mContext, "首页分类.json");


        ArrayList<SquareBean> data = (ArrayList<SquareBean>) FastJsonParser.getInstance().listFromJson(jsonDta, SquareBean.class);

        Observable.from(data)
                .subscribe(new Action1<SquareBean>() {
                    @Override
                    public void call(SquareBean squareBean) {
                        squareBean.setTitle(squareBean.getTitle() + squareBean.getResId());
                        squareBean.setResId(R.drawable.ic_donut_small_black_24dp);

                    }
                });

        lifeAdapter.setData(data);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 4);
        recycleViewLife.setLayoutManager(layoutManager);
        recycleViewLife.setAdapter(lifeAdapter);

    }

    @Override
    public void error() {
        showToast("加载失败！");
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
