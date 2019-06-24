package comte.example.herve.baseapp.ui.main.Fragments.life.presenter;


import java.util.ArrayList;

import comte.example.herve.baseapp.R;
import comte.example.herve.baseapp.base.presenter.MvpBasePresenter;
import comte.example.herve.baseapp.bean.SquareBean;
import comte.example.herve.baseapp.utils.json.GsonUtils;
import comte.example.herve.baseapp.utils.string.StringUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

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

    //模拟操作
    String jsonDta = StringUtils.getJson(getContext(), "首页分类.json");
    ArrayList<SquareBean> data =
        GsonUtils.fromJsonArray(jsonDta, SquareBean.class);

    Observable.fromIterable(data)
        .map(new Function<SquareBean, SquareBean>() {
          @Override
          public SquareBean apply(SquareBean squareBean) throws Exception {
            squareBean.setTitle(squareBean.getTitle() + squareBean.getResId());
            squareBean.setResId(R.drawable.ic_donut_small_black_24dp);
            return squareBean;
          }
        }).as(bindLifecycle()).subscribe(new Observer<SquareBean>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(SquareBean squareBean) {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });

  }

  @Override
  public void showDialog() {

  }

  @Override
  public void dismissDialog() {

  }
}
