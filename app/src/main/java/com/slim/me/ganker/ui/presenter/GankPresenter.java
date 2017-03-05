package com.slim.me.ganker.ui.presenter;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;

import com.slim.me.ganker.R;
import com.slim.me.ganker.manager.ApiManager;
import com.slim.me.ganker.constant.Constants;
import com.slim.me.ganker.data.GankData;
import com.slim.me.ganker.data.entity.Gank;
import com.slim.me.ganker.manager.SuperManager;
import com.slim.me.ganker.ui.view.IGankView;
import com.slim.me.ganker.util.GLog;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Slim on 2017/3/3.
 */

public class GankPresenter extends BasePresenter<IGankView> {

    public static final String TAG = "GankPresenter";

    private int mPage = 0;
    private final int REQUEST_NUM = 10;

    private final int TYPE_LOAD_MORE = 1;
    private final int TYPE_REFRESH = 2;

    private String mType;

    private List<Gank> mGanks = new ArrayList<>();

    /**
     * @param view
     * @param gankType {@link com.slim.me.ganker.constant.Constants.GankType}
     */
    public GankPresenter(@NonNull IGankView view, @NonNull String gankType) {
        super(view);
        if(!Constants.GankType.isVaildType(gankType)) {
            throw new IllegalArgumentException("error gankType:" + gankType);
        }
        mType = gankType;
    }

    public void loadMore() {
        requestGank(REQUEST_NUM, mPage + 1, TYPE_LOAD_MORE);
    }

    public void refresh() {
        requestGank(REQUEST_NUM, 1, TYPE_REFRESH);
    }

    private synchronized void requestGank(int number, final int page, final int loadType) {
        GLog.d(TAG,"requestGank, number=" + number + ",page=" + page + ",loadType=" + loadType);

        ApiManager manager = (ApiManager) SuperManager.getAppManager(SuperManager.API_MANAGER);
        Subscription subscription = manager
                .getGankApi()
                .getGankData(mType, number, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankData>() {
                    @Override
                    public void onCompleted() {
                        GLog.d(TAG, "requestGank : onComplete.");
                        getView().setRefreshing(false);
                        mPage = page;
                    }

                    @Override
                    public void onError(Throwable e) {
                        GLog.e(TAG, "requestGank : " + e.toString());
                        getView().setRefreshing(false);
                        getView().showSnack(R.string.network_error, Snackbar.LENGTH_SHORT);
                    }

                    @Override
                    public void onNext(GankData gankData) {
                        GLog.d(TAG, "requestGank : " + gankData.results.toString());
                        if(loadType == TYPE_REFRESH) {
                            mGanks.clear();
                            mGanks.addAll(gankData.results);
                        }else if(loadType == TYPE_LOAD_MORE) {
                            mGanks.addAll(gankData.results);
                        }else {

                        }
                        getView().updateGanks(mGanks);
                    }
                });
        addSubscription(subscription);
    }

    @NonNull
    public List<Gank> getGankList() {
        return new ArrayList<>(mGanks);
    }

}
