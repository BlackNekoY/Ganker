package com.slim.me.ganker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.slim.me.ganker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by slimxu on 2017/3/1.
 */
public class WebActivity extends ToolbarActivity {

    public static final String TAG = "WebActivity";

    public static final String EXTRA_URL = "EXTRA_URL";

    @BindView(R.id.webview) WebView webView;

    private String mUrl;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        parseIntent();
        setupWebview();
    }

    private void setupWebview() {
        WebSettings setting = webView.getSettings();
        webView.loadUrl(mUrl);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
    }

    private void parseIntent() {
        Intent intent = getIntent();
        if(intent != null) {
            mUrl = intent.getStringExtra(EXTRA_URL);
        }
    }

    public static Intent launchWebActivity(@NonNull String url, @NonNull Activity startActivity) {
        Intent intent = new Intent(startActivity, WebActivity.class);
        intent.putExtra(EXTRA_URL, url);
        return intent;
    }
}
