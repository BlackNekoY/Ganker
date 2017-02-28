package com.slim.me.ganker.ui.presenter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;

import com.slim.me.ganker.constant.Constants;
import com.slim.me.ganker.ui.view.IPhotoView;
import com.slim.me.ganker.util.BitmapUtil;
import com.slim.me.ganker.util.GLog;

import java.io.File;
import java.io.IOError;
import java.io.IOException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Slim on 2017/2/27.
 */

public class PhotoPresenter extends BasePresenter<IPhotoView> {

    public static final String TAG = "PhotoPresenter";

    private final String PHOTO_SAVE_PATH = Constants.PHOTO_SAVE_PATH;

    public PhotoPresenter(@NonNull IPhotoView view) {
        super(view);
    }

    public void savePhoto(@NonNull final Bitmap bitmap, @NonNull final String fileName) {
        GLog.d(TAG, "savePhoto, fileName:" + fileName);
        getView().setProgressVisible(true);
        Subscription subscription = Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                File photoFile = BitmapUtil.saveBitmap2File(bitmap, PHOTO_SAVE_PATH, fileName);
                if (photoFile != null) {
                    subscriber.onNext(photoFile);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new IOException("saveBitmap failed."));
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<File>() {
                    @Override
                    public void onCompleted() {
                        getView().setProgressVisible(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        GLog.e(TAG, e.toString());
                        getView().setProgressVisible(false);
                        getView().showSnack("保存失败", Snackbar.LENGTH_LONG);
                    }

                    @Override
                    public void onNext(File file) {
                        String filePath = file.getAbsolutePath();
                        getView().showSnack("已保存在:" + filePath, Snackbar.LENGTH_LONG);
                    }
                });
        addSubscription(subscription);
    }
}
