package com.slim.me.ganker.ui.widget;

import android.os.Parcel;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;

/**
 * Created by Slim on 2017/2/26.
 */

public class URLSpanNoUnderLine extends URLSpan {

    public URLSpanNoUnderLine(String url) {
        super(url);
    }

    public URLSpanNoUnderLine(Parcel src) {
        super(src);
    }


    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
        super.onClick(widget);
    }
}
