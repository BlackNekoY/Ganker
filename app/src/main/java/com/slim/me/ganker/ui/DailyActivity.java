package com.slim.me.ganker.ui;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.slim.me.ganker.R;
import com.slim.me.ganker.ui.adapter.DailyPagerAdapter;
import com.slim.me.ganker.ui.event.JumpToWebEvent;
import com.slim.me.ganker.ui.fragment.BaseFragment;
import com.slim.me.ganker.ui.fragment.DailyFragment;
import com.slim.me.ganker.util.DateUtil;
import com.slim.me.ganker.util.GLog;
import com.slim.me.ganker.util.UiUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Slim on 2017/2/22.
 */
public class DailyActivity extends ToolbarActivity {

    public static final String TAG = "DailyActivity";
    private static final String EXTRA_DATE = "EXTRA_PARAMS";
    private static final String EXTRA_MEIZHI_URL = "EXTRA_MEIZHI_URL";

    private boolean mImageLoadSuccess;
    private Date[] mDateArray;
    private String[] mUrlArray;

    private ArrayList<BaseFragment> mFragments = new ArrayList<>();

    @BindView(R.id.meizhi_image)
    ImageView mMeizhiImage;

    @BindView(R.id.collapsing_layout)
    CollapsingToolbarLayout mCollapsingLayout;

    @BindView(R.id.daily_pager)
    ViewPager dailyPager;

    @BindView(R.id.daily_tab)
    TabLayout dailyTab;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        if(!parseLaunchParams()) {
            finish();
            return;
        }

        setToolbarTitle(mDateArray[0]);
        initDailyPager();
        initDailyTab();
        setupMeizhiImage();
    }

    private void initDailyPager() {
        for(Date date : mDateArray) {
            mFragments.add(DailyFragment.newInstance(date));
        }

        dailyPager.setAdapter(new DailyPagerAdapter(getSupportFragmentManager(), mFragments));
        dailyPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setToolbarTitle(mDateArray[position]);
                loadMeizhiImage(mUrlArray[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initDailyTab() {
        if(mDateArray.length == 1) {
            dailyTab.setVisibility(View.GONE);
            return;
        }
        for(int i = 0; i < mDateArray.length; i++)  {
            dailyTab.addTab(dailyTab.newTab());
        }
        dailyTab.setupWithViewPager(dailyPager);
    }

    private boolean parseLaunchParams() {
        Intent intent = getIntent();
        if(intent != null) {
            mDateArray = (Date[]) intent.getSerializableExtra(EXTRA_DATE);
            mUrlArray = intent.getStringArrayExtra(EXTRA_MEIZHI_URL);

            if(mDateArray == null || mDateArray.length <= 0
                    || mUrlArray == null || mUrlArray.length <= 0) {
                return false;
            }

            return true;
        }else {
            GLog.e(TAG, "getIntent() return null.");
            return false;
        }
    }

    private void setupMeizhiImage() {
        ViewGroup.LayoutParams params = mMeizhiImage.getLayoutParams();
        params.height = UiUtil.getScreenHeight(this) * 2 / 3;
        mMeizhiImage.setLayoutParams(params);
        loadMeizhiImage(mUrlArray[0]);
    }

    private void loadMeizhiImage(String url) {
        mImageLoadSuccess = false;
        Glide.with(this)
                .load(url)
                .centerCrop()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        mImageLoadSuccess = false;
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if(resource != null) {
                            mImageLoadSuccess = true;
                        }else {
                            mImageLoadSuccess = false;
                        }
                        return false;
                    }
                })
                .into(mMeizhiImage);
    }


    private void setToolbarTitle(Date date) {
        String title = DateUtil.formatDate(date);
        setToolbarTitle(title);
        mCollapsingLayout.setTitle(title);
        mCollapsingLayout.setExpandedTitleColor(Color.parseColor("#00ffffff"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JumpToWebEvent event) {
        String url = event.url;
        String title = event.title;
        Intent intent = WebActivity.launchWebActivity(url, title, this);
        startActivity(intent);
    }

    @OnClick(R.id.meizhi_image)
    public void jumpToPhotoActivity() {
        if(mImageLoadSuccess) {
            // 直接传Bitmap会失败，太大了
            int position = dailyPager.getCurrentItem();
            Intent intent = PhotoActivity.launchPhotoActivity(mUrlArray[position], this);
            startActivity(intent);
        }else {
            GLog.d(TAG, "load image failed, can't not jump to PhotoActivity");
        }
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
    protected int getContentViewId() {
        return R.layout.activity_gank;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public static Intent getLaunchIntent(@NonNull Date[] dateArray, @NonNull String[] urlArray, Activity launchActivity) {
        Intent intent = new Intent(launchActivity, DailyActivity.class);
        intent.putExtra(EXTRA_DATE, dateArray);
        intent.putExtra(EXTRA_MEIZHI_URL, urlArray);
        return intent;
    }
}
