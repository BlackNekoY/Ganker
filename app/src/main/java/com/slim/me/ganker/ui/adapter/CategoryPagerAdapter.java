package com.slim.me.ganker.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.slim.me.ganker.ui.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Slim on 2017/3/3.
 */

public class CategoryPagerAdapter extends FragmentPagerAdapter {

    @NonNull
    private final ArrayList<BaseFragment> mFragments = new ArrayList<>();

    public CategoryPagerAdapter(FragmentManager fm, @NonNull ArrayList<BaseFragment> fragments) {
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

    @Override
    public CharSequence getPageTitle(int position) {
        BaseFragment fragment = mFragments.get(position);
        return fragment.getTitle();
    }
}
