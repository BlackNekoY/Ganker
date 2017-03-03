package com.slim.me.ganker.ui.presenter;

import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;

import com.slim.me.ganker.R;
import com.slim.me.ganker.data.MeizhiData;
import com.slim.me.ganker.data.entity.Meizhi;
import com.slim.me.ganker.api.ApiManager;
import com.slim.me.ganker.ui.view.IMeizhiView;
import com.slim.me.ganker.util.GLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * TODO Presenter可以将不同业务抽成不同的Manager，比如
 * Meizhi下载：MeizhiDownloadManager
 * Meizhi写数据库:MeizhiDatabaseManager等，Presenter不操作具体逻辑，分发给具体的model
 *
 * Created by Slim on 2017/2/16.
 */
public class MeizhiPresenter extends BasePresenter<IMeizhiView>{

    public static final String TAG = "MeizhiPresenter";

    private int mPage = 0;
    private final int REQUEST_NUM = 10;

    private final int TYPE_LOAD_MORE = 1;
    private final int TYPE_REFRESH = 2;

    private List<Meizhi> mMeizhis = new ArrayList<>();

    private Handler mUiHandler = new Handler(Looper.getMainLooper());

    public MeizhiPresenter(IMeizhiView view) {
        super(view);
    }

    public void loadMore() {
        requestMeizhi(REQUEST_NUM, mPage + 1, TYPE_LOAD_MORE);
    }

    public void refresh() {
        requestMeizhi(REQUEST_NUM, 1, TYPE_REFRESH);
    }

    private synchronized void requestMeizhi(int number, final int page, final int loadType) {

        GLog.d(TAG,"requestMeizhi, number=" + number + ",page=" + page + ",loadType=" + loadType);

        Subscription subscription = ApiManager.getInstance().getGankApi()
                .getMeizhi(number, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MeizhiData>() {
                    @Override
                    public void onCompleted() {
                        GLog.d(TAG, "requestMeizhi : onComplete.");
                        IMeizhiView view = getView();
                        view.setRefreshing(false);
                        mPage = page;
                    }

                    @Override
                    public void onError(Throwable e) {
                        GLog.e(TAG, "requestMeizhi : " + e.toString());
                        IMeizhiView view = getView();
                        view.setRefreshing(false);
                        view.showSnack(R.string.network_error, Snackbar.LENGTH_SHORT);

                    }

                    @Override
                    public void onNext(MeizhiData meizhiData) {
                        GLog.d(TAG, "requestMeizhi : " + meizhiData.results.toString());
                        IMeizhiView view = getView();
                        if(loadType == TYPE_REFRESH) {
                            mMeizhis.clear();
                            mMeizhis.addAll(meizhiData.results);
                        }else if(loadType == TYPE_LOAD_MORE) {
                            mMeizhis.addAll(meizhiData.results);
                        }else {

                        }
                        view.updateMeizhi(mMeizhis);
                    }
                });
        addSubscription(subscription);
    }
}
