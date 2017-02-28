package com.slim.me.ganker.ui.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.slim.me.ganker.util.GLog;

import java.util.List;

/**
 * Created by slimxu on 2017/2/28.
 */
public class SnackbarBehavior extends CoordinatorLayout.Behavior<View> {

    public SnackbarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float offset = getTranslationYForSnackbar(parent, child);
        GLog.d("offset","offset:" + offset);
        ViewCompat.setTranslationY(child, offset);
        return false;
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency) {
        ViewCompat.setTranslationY(child, 0);
    }

    private float getTranslationYForSnackbar(CoordinatorLayout parent, View child) {
        float minOffset = 0;
        final List<View> dependencies = parent.getDependencies(child);
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            final View view = dependencies.get(i);
            if (view instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(child, view)) {
                minOffset = Math.min(minOffset,
                        ViewCompat.getTranslationY(view) - view.getHeight());
            }
        }
        return minOffset;
    }
}
