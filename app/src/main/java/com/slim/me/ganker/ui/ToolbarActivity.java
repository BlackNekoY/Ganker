package com.slim.me.ganker.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.slim.me.ganker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Slim on 2017/2/16.
 */
public abstract class ToolbarActivity extends BaseActivity {

    @LayoutRes
    protected abstract int getContentViewId();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.app_bar)
    AppBarLayout appbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        ButterKnife.bind(this);

        checkToolbar();
        setSupportActionBar(toolbar);
        if(homeButtonIsEnabled()) {
            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
            }
        }
    }


    private void checkToolbar() {
        if(toolbar == null || appbarLayout == null) {
            throw new IllegalStateException("Activity extends ToolbarActivity must have Toolbar and AppBarLayout in XML.");
        }
    }


    protected Toolbar getToolbar() {
        return toolbar;
    }

    protected AppBarLayout getAppBar() {
        return appbarLayout;
    }

    protected void setToolbarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected void setAppbarAlpha(float alpha) {
        appbarLayout.setAlpha(alpha);
    }

    protected boolean homeButtonIsEnabled() {
        return false;
    }

}
