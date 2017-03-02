package com.slim.me.ganker.ui;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.slim.me.ganker.R;
import com.slim.me.ganker.data.AllData;
import com.slim.me.ganker.data.entity.Gank;
import com.slim.me.ganker.ui.event.JumpToWebEvent;
import com.slim.me.ganker.ui.presenter.GankPresenter;
import com.slim.me.ganker.ui.provider.CategoryViewProvider;
import com.slim.me.ganker.ui.provider.GanhuoViewProvider;
import com.slim.me.ganker.ui.view.IGankView;
import com.slim.me.ganker.util.GLog;
import com.slim.me.ganker.util.UiUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Slim on 2017/2/22.
 */
public class GankActivity extends ToolbarActivity implements IGankView {

    public static final String TAG = "GankActivity";
    private static final String EXTRA_DATE = "EXTRA_PARAMS";
    private static final String EXTRA_MEIZHI_URL = "EXTRA_URL";

    private String mMeizhiUrl;
    private boolean mImageLoadSuccess;
    private Date mDate;
    private int mYear;
    private int mMonth;
    private int mDay;

    private MultiTypeAdapter mAdapter;

    @BindView(R.id.meizhi_image) ImageView mMeizhiImage;

    @BindView(R.id.gank_list) RecyclerView mGankListView;

    @BindView(R.id.progress_layout) LinearLayout mProgressLayout;

    @BindView(R.id.progress) ContentLoadingProgressBar mProgressBar;

    @BindView(R.id.collapsing_layout) CollapsingToolbarLayout mCollapsingLayout;

    private GankPresenter mGankPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        parseLaunchParams();
        if (!checkLaunchParams()) {
            finish();
            return;
        }

        initToolbar();
        initRecyclerView();

        mProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.material_purple_500), android.graphics.PorterDuff.Mode.MULTIPLY);
        setupMeizhiImage();

        mGankPresenter = new GankPresenter(this);
        mGankPresenter.requestGankDaily(String.valueOf(mYear), String.valueOf(mMonth), String.valueOf(mDay));

    }

    private void parseLaunchParams() {
        Intent intent = getIntent();
        if(intent != null) {
            mDate = (Date) intent.getSerializableExtra(EXTRA_DATE);
            mMeizhiUrl = intent.getStringExtra(EXTRA_MEIZHI_URL);
            GLog.d(TAG, "parseLaunchParams:[Date=" + mDate + ",url=" + mMeizhiUrl + ",name:" + "]");
        }else {
            GLog.e(TAG, "getIntent() return null.");
        }
    }

    private void setupMeizhiImage() {
        ViewGroup.LayoutParams params = mMeizhiImage.getLayoutParams();
        params.height = UiUtil.getScreenHeight(this) * 2 / 3;
        mMeizhiImage.setLayoutParams(params);
        Glide.with(this)
                .load(mMeizhiUrl)
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


    private void initToolbar() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        setToolbarTitle(format.format(mDate));
        mCollapsingLayout.setTitle(format.format(mDate));
        mCollapsingLayout.setExpandedTitleColor(Color.parseColor("#00ffffff"));

        calendar.setTime(mDate);
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH) + 1; // January month 0 in Calendar
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        GLog.d(TAG, "initToolbar, year=" + mYear + ",month=" + mMonth + ",day=" + mDay);

        getAppBar().addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int range = appBarLayout.getTotalScrollRange();
                if(verticalOffset > -range / 2) {
                    //展开超一半
                }else {
                    //展开还没有一半
                }
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mGankListView.setLayoutManager(layoutManager);

        mAdapter = new MultiTypeAdapter();
        mAdapter.register(CategoryViewProvider.Category.class, new CategoryViewProvider());
        mAdapter.register(Gank.class, new GanhuoViewProvider());
        mGankListView.setAdapter(mAdapter);

    }

    private boolean checkLaunchParams() {
        if(mDate == null || TextUtils.isEmpty(mMeizhiUrl)) {
            GLog.e(TAG, "LaunchParams is illegal, finish GankActivity. Date:" + mDate + ",meizhiUrl:" + mMeizhiUrl);
            return false;
        }
        return true;
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
            Intent intent = PhotoActivity.launchPhotoActivity(mMeizhiUrl, this);
            startActivity(intent);
        }else {
            GLog.d(TAG, "load image failed, can't not jump to PhotoActivity");
        }
    }

    @Override
    public void updateDaily(AllData data) {
        if(data == null) {
            GLog.e(TAG, "updateDaily: data is null.");
            return;
        }
        Items items = new Items();
        final LinkedHashMap<String, ArrayList<Gank>> map = data.results;
        for (Map.Entry<String, ArrayList<Gank>> entry : map.entrySet()) {
            String category = entry.getKey();
            ArrayList<Gank> ganks = entry.getValue();
            if(ganks != null && !ganks.isEmpty() && !"福利".equals(category)) {
                items.add(new CategoryViewProvider.Category(category));
                items.addAll(ganks);
            }
        }
        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setLoadingState(boolean isLoading) {
        if(isLoading) {
            mProgressLayout.setVisibility(View.VISIBLE);
            mProgressBar.show();
        }else {
            mProgressLayout.setVisibility(View.GONE);
            mProgressBar.hide();
        }
    }

    @Override
    protected void unsubscribePresenterSubscription() {
        mGankPresenter.unSubscribeAllSubscription();
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

    public static Intent getLaunchIntent(@NonNull Date date, String meizhiUrl, Activity launchActivity) {
        Intent intent = new Intent(launchActivity, GankActivity.class);
        intent.putExtra(EXTRA_DATE, date);
        intent.putExtra(EXTRA_MEIZHI_URL, meizhiUrl);
        return intent;
    }
}
