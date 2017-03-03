package com.slim.me.ganker.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.slim.me.ganker.ui.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Slim on 2017/3/3.
 */

public class CategoryPagerAdapter extends FragmentStatePagerAdapter {

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

    @Nullable
    public BaseFragment getFragment(int index) {
        if(index >= 0 && index < mFragments.size()) {
            return mFragments.get(index);
        }
        return null;
    }

}
