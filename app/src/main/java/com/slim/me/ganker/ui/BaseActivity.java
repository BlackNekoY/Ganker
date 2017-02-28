package com.slim.me.ganker.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.slim.me.ganker.R;
import com.slim.me.ganker.ui.view.IBaseView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * all Activity's father, manage :
 * 1、Subscription
 *
 * Created by Slim on 2017/2/16.
 */

public class BaseActivity extends AppCompatActivity implements IBaseView{

    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getCompositeSubscription() {
        checkCompositeSubscription();
        return mCompositeSubscription;
    }

    public void addSubscription(Subscription subscription) {
        checkCompositeSubscription();
        mCompositeSubscription.add(subscription);
    }

    private void checkCompositeSubscription() {
        if(mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
        unsubscribePresenterSubscription();
    }

    protected void unsubscribePresenterSubscription() {

    }

    @Override
    public void showSnack(String text, int duration) {
        Snackbar.make(getWindow().getDecorView(), text, duration).show();
    }

    @Override
    public void showSnack(@StringRes int resId, int duration) {
        Snackbar.make(getWindow().getDecorView(), resId, duration).show();
    }
}
