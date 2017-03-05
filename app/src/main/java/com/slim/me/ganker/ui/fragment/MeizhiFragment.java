package com.slim.me.ganker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slim.me.ganker.R;
import com.slim.me.ganker.data.entity.Meizhi;
import com.slim.me.ganker.ui.DailyActivity;
import com.slim.me.ganker.ui.adapter.MeizhiListAdapter;
import com.slim.me.ganker.ui.presenter.MeizhiPresenter;
import com.slim.me.ganker.ui.view.IMeizhiView;
import com.slim.me.ganker.util.GLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Meizhi fragment
 * Created by Slim on 2017/2/16.
 */
public class MeizhiFragment extends BaseFragment implements IMeizhiView {

    public static final String TAG = "MeizhiFragment";

    private MeizhiPresenter mPresenter;
    private View mContentView;
    private MeizhiListAdapter mAdapter;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.meizhi_list)
    RecyclerView mMeizhiList;

    private boolean mIsRefreshing;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MeizhiPresenter(this);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_meizhi, null);
            ButterKnife.bind(this, mContentView);

            setupRefreshLayout();
            setupRecyclerView();

            mPresenter.queryMeizhiFromDatabase();
            mPresenter.loadMore();
        }
        return mContentView;
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mMeizhiList.setLayoutManager(layoutManager);

        mAdapter = new MeizhiListAdapter(getContext(), null);
        mMeizhiList.setAdapter(mAdapter);

        mMeizhiList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if(mIsRefreshing) {
                        return;
                    }
                    StaggeredGridLayoutManager manager = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager());
                    int[] lastPos = new int[manager.getSpanCount()];
                    manager.findLastVisibleItemPositions(lastPos);
                    int lastPosition = findMax(lastPos);
                    if (lastPosition == manager.getItemCount() - 1) {
                        //滑动到最后
                        setRefreshing(true);
                        mPresenter.loadMore();
                    }
                }
            }
        });
    }

    private void setupRefreshLayout() {
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.material_purple_500));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mIsRefreshing = true;
                mPresenter.refresh();
            }
        });
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    protected void unsubscribePresenterSubscription() {
        mPresenter.unSubscribeAllSubscription();
    }


    @Override
    public String getTitle() {
        return getResources().getString(R.string.fuli);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        mIsRefreshing = refreshing;
        mRefreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void updateMeizhi(List<Meizhi> meizhiList) {
        GLog.d(TAG, "updateMeizhi: size=" + meizhiList.size());
        mAdapter.updateMeizhiList(meizhiList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MeizhiListAdapter.MeizhiClickEvent event) {
        Meizhi meizhi = event.meizhi;
        Intent intent = DailyActivity.getLaunchIntent(meizhi.publishedAt, meizhi.url, getActivity());
        getActivity().startActivity(intent);
    }
}
