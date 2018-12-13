package comte.example.herve.baseapp.base.view;

import android.os.Bundle;

import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created           :Herve on 2016/9/27.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/9/27
 * @ projectName     :OurSchool
 * @ version
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    protected AppCompatActivity mActivity;
    protected Unbinder mUnBinder;
    private String TAG = getClass().getSimpleName();

    protected abstract int initLayoutId();

    protected abstract void findViewById();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayoutId());
        mUnBinder = ButterKnife.bind(this);
        mActivity = this;
        baseActivityCreate();

        findViewById();

        initData();

        initView();

        initListener();
    }

    protected void baseActivityCreate() {

    }

    @Override
    protected void onDestroy() {
        mUnBinder.unbind();
        super.onDestroy();
    }
}
