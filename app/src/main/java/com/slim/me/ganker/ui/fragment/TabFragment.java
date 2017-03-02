package com.slim.me.ganker.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slim.me.ganker.R;
import com.slim.me.ganker.ui.presenter.TabPresenter;
import com.slim.me.ganker.ui.view.ITabView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Slim on 2017/3/3.
 */
public class TabFragment extends BaseFragment implements ITabView {

    private String mTitle;

    private TabPresenter mPresenter;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    public TabFragment setTitle(@NonNull String title) {
        mTitle = title;
        return this;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new TabPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, null);
        ButterKnife.bind(this, view);
        initRefreshLayout();
        return view;
    }

    private void initRefreshLayout() {
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.material_purple_500));
    }

    public static TabFragment newInstance(@NonNull String title) {
        TabFragment fragment = new TabFragment();
        fragment.setTitle(title);
        return fragment;
    }
}
