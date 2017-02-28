package com.slim.me.ganker.ui.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Slim on 2017/2/16.
 */
public class BasePresenter<V> {

    @NonNull
    protected V view;
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    public BasePresenter(@NonNull V view) {
        this.view = view;
    }

    @NonNull
    protected V getView() {
        return view;
    }


    public void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    public void unSubscribeAllSubscription() {
        compositeSubscription.unsubscribe();
    }

}
