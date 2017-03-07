package com.slim.me.ganker.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.slim.me.ganker.ui.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Slim on 2017/3/7.
 */
public class DailyPagerAdapter extends FragmentStatePagerAdapter {

    @NonNull
    private final ArrayList<BaseFragment> mFragments = new ArrayList<>();

    public DailyPagerAdapter(FragmentManager fm, @NonNull ArrayList<BaseFragment> fragments) {
        super(fm);
        mFragments.addAll(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
