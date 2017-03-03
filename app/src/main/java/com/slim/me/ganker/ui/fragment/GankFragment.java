package com.slim.me.ganker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slim.me.ganker.R;
import com.slim.me.ganker.data.entity.Gank;
import com.slim.me.ganker.ui.WebActivity;
import com.slim.me.ganker.ui.adapter.GankListAdapter;
import com.slim.me.ganker.ui.presenter.GankPresenter;
import com.slim.me.ganker.ui.view.IGankView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Slim on 2017/3/3.
 */
public class GankFragment extends BaseFragment implements IGankView, GankListAdapter.OnGankClickListener {

    public static final String TAG = "GankFragment";

    private String mTitle;
    private String mGankType;
    public boolean mIsRefreshing;

    private GankPresenter mPresenter;
    private GankListAdapter mAdapter;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private View mRoot;

    private Handler mUiHandler = new Handler(Looper.getMainLooper());

    /**
     * @param title Fragment显示在TabLayout上的title
     * @param gankType Fragment请求的数据类型
     */
    public static GankFragment newInstance(@NonNull String title, @NonNull String gankType) {
        GankFragment fragment = new GankFragment();
        fragment.setTitle(title);
        fragment.setGankType(gankType);
        return fragment;
    }


    private void setTitle(@NonNull String title) {
        mTitle = title;
    }

    private void setGankType(@NonNull String gankType) {
        mGankType = gankType;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRoot == null) {
            mPresenter = new GankPresenter(this, mGankType);
            mRoot = inflater.inflate(R.layout.fragment_tab, null);
            ButterKnife.bind(this, mRoot);
            initRefreshLayout();
            initRecyclerView();

            setRefreshing(true);
            mPresenter.loadMore();
        }
        return mRoot;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void initRefreshLayout() {
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.material_purple_500));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mIsRefreshing = true;
                mPresenter.refresh();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new GankListAdapter(getContext(), null);
        mAdapter.setOnGankClickListener(this);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mIsRefreshing) {
                        return;
                    }
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                    int itemCount = layoutManager.getItemCount();
                    if(lastVisiblePosition == itemCount - 1) {
                        setRefreshing(true);
                        mPresenter.loadMore();
                    }
                }
            }
        });
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        if(!refreshing) {
            //如果不刷新了，delay个500ms
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIsRefreshing = false;
                    refreshLayout.setRefreshing(false);
                }
            }, 500);
        }else {
            mIsRefreshing = true;
            refreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void updateGanks(List<Gank> gankList) {
        mAdapter.updateGankList(gankList);
    }

    @Override
    public void onGankClick(Gank gank) {
        Intent intent = WebActivity.launchWebActivity(gank.url, gank.desc, getActivity());
        startActivity(intent);
    }

    public void onToolbarDoubleClick() {
        if(mAdapter.getItemCount() > 0) {
            recyclerView.smoothScrollToPosition(0);
        }
    }
}
