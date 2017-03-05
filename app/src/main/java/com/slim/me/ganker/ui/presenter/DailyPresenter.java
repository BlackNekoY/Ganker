package com.slim.me.ganker.ui.presenter;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;

import com.slim.me.ganker.R;
import com.slim.me.ganker.manager.ApiManager;
import com.slim.me.ganker.data.AllData;
import com.slim.me.ganker.manager.SuperManager;
import com.slim.me.ganker.ui.view.IDailyView;
import com.slim.me.ganker.util.GLog;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Slim on 2017/2/25.
 */

public class DailyPresenter extends BasePresenter<IDailyView> {

    public static final String TAG = "DailyPresenter";

    public DailyPresenter(@NonNull IDailyView view) {
        super(view);
    }

    public void requestGankDaily(@NonNull String year, @NonNull String month, @NonNull String day) {
        GLog.d(TAG, "requestGankDaily, year=" + year + ",month=" + month + ",day=" + day);
        getView().setLoadingState(true);
        ApiManager manager = (ApiManager) SuperManager.getAppManager(SuperManager.API_MANAGER);
        Subscription subscription = manager.getGankApi()
                .getDailyData(year, month, day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AllData>() {
                    @Override
                    public void onCompleted() {
                        GLog.d(TAG, "onComplete");
                        getView().setLoadingState(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        GLog.d(TAG, "onError, " + e.toString());
                        getView().setLoadingState(false);
                        getView().showSnack(R.string.network_error, Snackbar.LENGTH_SHORT);
                    }

                    @Override
                    public void onNext(AllData allData) {
                        GLog.d(TAG, "onNext, Data:" + allData.toString());
                        getView().updateDaily(allData);
                    }
                });
        addSubscription(subscription);
    }

}
