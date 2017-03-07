package com.slim.me.ganker.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.slim.me.ganker.R;
import com.slim.me.ganker.data.AllData;
import com.slim.me.ganker.data.entity.Gank;
import com.slim.me.ganker.ui.presenter.DailyPresenter;
import com.slim.me.ganker.ui.provider.CategoryViewProvider;
import com.slim.me.ganker.ui.provider.GanhuoViewProvider;
import com.slim.me.ganker.ui.view.IDailyView;
import com.slim.me.ganker.util.DateUtil;
import com.slim.me.ganker.util.GLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Slim on 2017/3/7.
 */
public class DailyFragment extends BaseFragment implements IDailyView{

    private static final String TAG = "DailyFragment";
    public static final String EXTRA_DATE = "EXTRA_DATE";

    private Date mDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String mMeizhiUrl;

    private View mRootView;

    @BindView(R.id.gank_list)
    RecyclerView mGankListView;

    @BindView(R.id.progress_layout)
    LinearLayout mProgressLayout;

    @BindView(R.id.progress)
    ContentLoadingProgressBar mProgressBar;

    private MultiTypeAdapter mAdapter;
    private DailyPresenter mDailyPresenter;

    @Override
    public String getTitle() {
        return DateUtil.formatDate(mDate);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArguments();
        mDailyPresenter = new DailyPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_daily, null);
            ButterKnife.bind(this, mRootView);
            initRecyclerView();

            mProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.material_purple_500), android.graphics.PorterDuff.Mode.MULTIPLY);
            mDailyPresenter.requestGankDaily(String.valueOf(mYear), String.valueOf(mMonth), String.valueOf(mDay));
        }
        return mRootView;
    }

    private void parseArguments() {
        Bundle data = getArguments();
        if(data != null) {
            mDate = (Date) data.getSerializable(EXTRA_DATE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDate);
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH) + 1; // January month 0 in Calendar
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
        }
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mGankListView.setLayoutManager(layoutManager);

        mAdapter = new MultiTypeAdapter();
        mAdapter.register(CategoryViewProvider.Category.class, new CategoryViewProvider());
        mAdapter.register(Gank.class, new GanhuoViewProvider());
        mGankListView.setAdapter(mAdapter);

    }

    public static DailyFragment newInstance(@NonNull Date date) {
        DailyFragment fragment = new DailyFragment();
        Bundle data = new Bundle();
        data.putSerializable(EXTRA_DATE, date);
        fragment.setArguments(data);
        return fragment;
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
            if("福利".equals(category) && ganks != null && !ganks.isEmpty()) {
                mMeizhiUrl = ganks.get(0).url;
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

}
