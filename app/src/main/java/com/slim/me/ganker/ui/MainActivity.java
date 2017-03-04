package com.slim.me.ganker.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.slim.me.ganker.R;
import com.slim.me.ganker.ui.fragment.BaseFragment;
import com.slim.me.ganker.ui.fragment.MeizhiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends ToolbarActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer)
    DrawerLayout mDrawer;
    @BindView(R.id.navigation)
    NavigationView mNavigationView;

    private ActionBarDrawerToggle mToggle;

    private MeizhiFragment mMeizhiFragment;

    private int mCurrentDrawerId;

    @Override
    @LayoutRes
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        setToolbarTitle(getResources().getString(R.string.app_name));

        setupDrawer();
        setupNavigation();

        mMeizhiFragment = new MeizhiFragment();
        selectFragment(mMeizhiFragment);

    }

    private void setupNavigation() {
        mNavigationView.setNavigationItemSelectedListener(this);

        int[][] states = new int[][]{
                new int[]{},
        };
        int[] colors = new int[]{
                getResources().getColor(R.color.material_purple_500),
        };

        mNavigationView.setItemIconTintList(new ColorStateList(states, colors));
    }

    private void setupDrawer() {
        mToggle = new ActionBarDrawerToggle(this, mDrawer, getToolbar(), R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawer.addDrawerListener(mToggle);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int color = typedValue.data;
        mDrawer.setStatusBarBackgroundColor(color);

        mToggle.syncState();
    }

    private void selectFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private void setDrawerItemChecked(int itemId) {
        MenuItem newItem = mNavigationView.getMenu().findItem(itemId);
        if(newItem != null) {
            MenuItem oldItem = mNavigationView.getMenu().findItem(mCurrentDrawerId);
            if(oldItem != null) {
                oldItem.setChecked(false);
            }
            mCurrentDrawerId = itemId;
            newItem.setChecked(true);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected boolean homeButtonIsEnabled() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_me:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_meizhi:
                if (mMeizhiFragment == null) {
                    mMeizhiFragment = new MeizhiFragment();
                }
                selectFragment(mMeizhiFragment);
                break;
            case R.id.drawer_category:
                Intent intent = CategoryActivity.launchActivity(this);
                startActivity(intent);
                break;
        }
        mDrawer.closeDrawer(Gravity.LEFT);
        return false;
    }
}
