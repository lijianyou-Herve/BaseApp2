package com.example.herve.baseapp.ui.main.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.herve.baseapp.ui.main.Fragments.life.LifeFragment;
import com.example.herve.baseapp.ui.main.Fragments.work.WorkFragment;
import com.herve.library.commonlibrary.base.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public class FragmentsAdapter extends FragmentPagerAdapter {


    List<BaseFragment> data = new ArrayList<>();


    private final String TAG = getClass().getSimpleName();

    Context mContext;

    public FragmentsAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;

    }

    public void setData(List<BaseFragment> data) {
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return LifeFragment.newInstance();

        } else {
            return WorkFragment.newInstance();
        }

    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).getTittle();
    }


}
