package com.slim.me.ganker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slim.me.ganker.R;
import com.slim.me.ganker.data.entity.Gank;
import com.slim.me.ganker.ui.WebActivity;
import com.slim.me.ganker.ui.adapter.GankListAdapter;
import com.slim.me.ganker.ui.event.JumpToWebEvent;
import com.slim.me.ganker.ui.presenter.TabPresenter;
import com.slim.me.ganker.ui.view.ITabView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Slim on 2017/3/3.
 */
public class TabFragment extends BaseFragment implements ITabView {

    private String mTitle;
    private String mGankType;
    public boolean mIsRefreshing;

    private TabPresenter mPresenter;
    private GankListAdapter mAdapter;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    /**
     * @param title Fragment显示在TabLayout上的title
     * @param gankType Fragment请求的数据类型
     */
    public static TabFragment newInstance(@NonNull String title, @NonNull String gankType) {
        TabFragment fragment = new TabFragment();
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
        mPresenter = new TabPresenter(this, mGankType);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, null);
        ButterKnife.bind(this, view);
        initRefreshLayout();
        initRecyclerView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
        mIsRefreshing = refreshing;
        refreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void updateGanks(List<Gank> gankList) {
        mAdapter.updateGankList(gankList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JumpToWebEvent event) {
        Intent intent = WebActivity.launchWebActivity(event.url, event.title, getActivity());
        startActivity(intent);
    }
}
