package com.slim.me.ganker.ui.view;

import android.support.annotation.StringRes;

/**
 * Created by Slim on 2017/2/24.
 */
public interface IBaseView {

    void showSnack(String text, int duration);

    void showSnack(@StringRes int resId, int duration);
}
