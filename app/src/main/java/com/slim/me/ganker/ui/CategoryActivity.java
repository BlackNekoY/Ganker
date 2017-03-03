package com.slim.me.ganker.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.slim.me.ganker.R;
import com.slim.me.ganker.constant.Constants;
import com.slim.me.ganker.ui.adapter.CategoryPagerAdapter;
import com.slim.me.ganker.ui.fragment.BaseFragment;
import com.slim.me.ganker.ui.fragment.GankFragment;
import com.slim.me.ganker.util.GLog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Slim on 2017/3/2.
 */
public class CategoryActivity extends ToolbarActivity {

    private static final String TAG = "CategoryActivity";
    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.content_pager)
    ViewPager content;

    private ArrayList<BaseFragment> mFragmentArray = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_category;
    }

    @Override
    protected boolean homeButtonIsEnabled() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLog.d(TAG, "onCreate");
        ButterKnife.bind(this);

        setToolbarTitle(getResources().getString(R.string.category));

        initFragmentList();
        initViewPager();
        initTabLayout();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        GLog.d(TAG, "onConfigurationChanged");
    }

    @Override
    protected void onToolbarDoubleClick() {
        BaseFragment fragment = mFragmentArray.get(content.getCurrentItem());
        if(fragment != null) {
            ((GankFragment) fragment).onToolbarDoubleClick();
        }
    }

    private void initFragmentList() {
        Resources resources = getResources();
        mFragmentArray.add(GankFragment.newInstance(resources.getString(R.string.android), Constants.GankType.ANDROID));
        mFragmentArray.add(GankFragment.newInstance(resources.getString(R.string.ios), Constants.GankType.IOS));
        mFragmentArray.add(GankFragment.newInstance(resources.getString(R.string.front_end), Constants.GankType.FRONT_END));
        mFragmentArray.add(GankFragment.newInstance(resources.getString(R.string.recommend), Constants.GankType.RECOMMAND));
        mFragmentArray.add(GankFragment.newInstance(resources.getString(R.string.expand_resource), Constants.GankType.EXPAND_RESOURCE));
        mFragmentArray.add(GankFragment.newInstance(resources.getString(R.string.relax_video), Constants.GankType.RELAX_VIDEO));
    }

    private void initViewPager() {
        CategoryPagerAdapter adapter = new CategoryPagerAdapter(getSupportFragmentManager(), mFragmentArray);
        content.setAdapter(adapter);
    }

    private void initTabLayout() {
        tabLayout.setupWithViewPager(content);
    }


    public static Intent launchActivity(@NonNull Activity startActivity) {
        return new Intent(startActivity, CategoryActivity.class);
    }
}
