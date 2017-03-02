package com.slim.me.ganker.ui.fragment;

import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.slim.me.ganker.ui.view.IBaseView;

import org.greenrobot.eventbus.EventBus;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Slim on 2017/2/16.
 */
public abstract class BaseFragment extends Fragment implements IBaseView {

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
    public void showSnack(String text, int duration) {
        View view = getView();
        if(view != null) {
            Snackbar.make(view, text, duration).show();
        }
    }

    @Override
    public void showSnack(@StringRes int resId, int duration) {
        View view = getView();
        if(view != null) {
            Snackbar.make(view, resId, duration).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //解绑Subscription
        if(mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
        unsubscribePresenterSubscription();
    }


    protected void unsubscribePresenterSubscription() {

    }

    public abstract String getTitle();

}
