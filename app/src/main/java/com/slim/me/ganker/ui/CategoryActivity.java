package com.slim.me.ganker.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.slim.me.ganker.R;
import com.slim.me.ganker.ui.adapter.CategoryPagerAdapter;
import com.slim.me.ganker.ui.fragment.BaseFragment;
import com.slim.me.ganker.ui.fragment.TabFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Slim on 2017/3/2.
 */
public class CategoryActivity extends ToolbarActivity {

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
        ButterKnife.bind(this);

        setToolbarTitle(getResources().getString(R.string.category));

        initFragmentList();
        initViewPager();
        initTabLayout();

    }

    private void initFragmentList() {
        Resources resources = getResources();
        mFragmentArray.add(TabFragment.newInstance(resources.getString(R.string.android)));
        mFragmentArray.add(TabFragment.newInstance(resources.getString(R.string.ios)));
        mFragmentArray.add(TabFragment.newInstance(resources.getString(R.string.front_end)));
        mFragmentArray.add(TabFragment.newInstance(resources.getString(R.string.recommend)));
        mFragmentArray.add(TabFragment.newInstance(resources.getString(R.string.expand_resource)));
        mFragmentArray.add(TabFragment.newInstance(resources.getString(R.string.relax_video)));
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
