package com.slim.me.ganker.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;

import com.slim.me.ganker.data.entity.Gank;
import com.slim.me.ganker.ui.widget.URLSpanNoUnderLine;

/**
 * generate normal string to spannableString
 * use SpannableStringBuilder
 * Created by Slim on 2017/2/26.
 */
public class TextKit {

    public static SpannableStringBuilder generate(@NonNull Gank gank, int linkColor, boolean isUnderLine) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        int start = 0;
        builder.append("· ");
        builder.setSpan(new StyleSpan(Typeface.BOLD), start, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        start = builder.length();
        builder.append(gank.desc);
        if(isUnderLine) {
            builder.setSpan(new URLSpan(gank.url), start, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }else {
            builder.setSpan(new URLSpanNoUnderLine(gank.url), start, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        builder.setSpan(new ForegroundColorSpan(linkColor), start, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        start = builder.length();
        builder.append("  (" + (TextUtils.isEmpty(gank.who)? "none" : gank.who) + ")");
        builder.setSpan(new StyleSpan(Typeface.ITALIC), start, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static void copyToClipboard(Context context, String content) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setText(content);
    }

}
