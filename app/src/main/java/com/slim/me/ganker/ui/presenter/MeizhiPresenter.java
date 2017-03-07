package com.slim.me.ganker.ui.presenter;

import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;

import com.slim.me.ganker.R;
import com.slim.me.ganker.data.MeizhiData;
import com.slim.me.ganker.data.entity.Meizhi;
import com.slim.me.ganker.data.entity.MeizhiDao;
import com.slim.me.ganker.manager.ApiManager;
import com.slim.me.ganker.manager.DatabaseManager;
import com.slim.me.ganker.manager.SuperManager;
import com.slim.me.ganker.ui.view.IMeizhiView;
import com.slim.me.ganker.util.GLog;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Slim on 2017/2/16.
 */
public class MeizhiPresenter extends BasePresenter<IMeizhiView> {

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

    public void queryMeizhiFromDatabase() {
        Observable.create(new Observable.OnSubscribe<List<Meizhi>>() {
            @Override
            public void call(Subscriber<? super List<Meizhi>> subscriber) {
                DatabaseManager manager = (DatabaseManager) SuperManager.getAppManager(SuperManager.DATABASE_MANAGER);
                List<Meizhi> meizhis = manager.getMeizhiDao().queryBuilder().list();
                subscriber.onNext(meizhis);
                subscriber.onCompleted();
            }
        })
                .flatMap(Observable::from)
                .toSortedList((meizhi1, meizhi2) ->
                        meizhi2.publishedAt.compareTo(meizhi1.publishedAt))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meizhis -> getView().updateMeizhi(meizhis));

    }

    private synchronized void requestMeizhi(int number, final int page, final int loadType) {

        GLog.d(TAG, "requestMeizhi, number=" + number + ",page=" + page + ",loadType=" + loadType);

        ApiManager manager = (ApiManager) SuperManager.getAppManager(SuperManager.API_MANAGER);
        Subscription subscription = manager.getGankApi()
                .getMeizhi(number, page)
                .map(meizhiData -> {
                    if (meizhiData.isError) {
                        Observable.error(new Exception("requestMeizhi error."));
                    } else {
                        return meizhiData.results;
                    }
                    return meizhiData.results;
                })
                .flatMap(Observable::from)
                .toSortedList((meizhi1, meizhi2) ->
                        meizhi2.publishedAt.compareTo(meizhi1.publishedAt))
                .doOnNext(this::saveMeizhiToDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Meizhi>>() {
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
                    public void onNext(List<Meizhi> meizhis) {
                        GLog.d(TAG, "requestMeizhi : " + meizhis.toString());
                        IMeizhiView view = getView();
                        if (loadType == TYPE_REFRESH) {
                            mMeizhis.clear();
                            mMeizhis.addAll(meizhis);
                        } else if (loadType == TYPE_LOAD_MORE) {
                            mMeizhis.addAll(meizhis);
                        } else {

                        }
                        view.updateMeizhi(mMeizhis);
                    }
                });
        addSubscription(subscription);
    }

    private void saveMeizhiToDatabase(List<Meizhi> meizhis) {
        DatabaseManager manager = (DatabaseManager) SuperManager.getAppManager(SuperManager.DATABASE_MANAGER);
        MeizhiDao dao = manager.getMeizhiDao();
        dao.insertOrReplaceInTx(meizhis);
    }

    public List<Meizhi> getMeizhis() {
        return new ArrayList<>(mMeizhis);
    }
}
