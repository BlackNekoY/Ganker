package com.slim.me.ganker.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.slim.me.ganker.R;
import com.slim.me.ganker.util.GLog;
import com.slim.me.ganker.util.TextKit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by slimxu on 2017/3/1.
 */
public class WebActivity extends ToolbarActivity {

    public static final String TAG = "WebActivity";

    public static final String EXTRA_URL = "EXTRA_URL";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";

    @BindView(R.id.webview)
    WebView webView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private MenuItem mRefreshItem;
    private boolean mIsRefreshing;

    private String mUrl;
    private String mTitle;

    private Handler mUiHandler = new Handler(Looper.getMainLooper());

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

        if(!TextUtils.isEmpty(mTitle)) {
            setWebTitle(mTitle);
        }
    }

    @Override
    protected boolean homeButtonIsEnabled() {
        return true;
    }


    private void setupWebview() {
        progressBar.setMax(100);
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setLoadWithOverviewMode(true);
        setting.setAppCacheEnabled(true);
        setting.setDomStorageEnabled(true);
        setting.setSupportZoom(true);
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.loadUrl(mUrl);

        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if(newProgress == 100) {
                    // 200s后再隐藏，不要太快
                    mUiHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(webView.getProgress() == 100) {
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    },200);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                }

                if(newProgress == 100 && mIsRefreshing) {
                    stopRefreshAnimation();
                }

                GLog.d(TAG, "onProgressChanged:" + newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                GLog.d(TAG, "onReceivedTitle:" + title);
                setWebTitle(title);
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
    }

    private boolean isProgressShown() {
        return progressBar.getVisibility() == View.VISIBLE;
    }

    private void refresh() {
        View refreshView = LayoutInflater.from(this).inflate(R.layout.refresh_view, null);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotation.setRepeatCount(Animation.INFINITE);
        refreshView.startAnimation(rotation);

        if(mRefreshItem != null) {
            mRefreshItem.setActionView(refreshView);
        }
        mIsRefreshing = true;
        webView.reload();
    }

    private void stopRefreshAnimation() {
        mIsRefreshing = false;
        mRefreshItem.getActionView().clearAnimation();
        mRefreshItem.setActionView(null);
    }


    private void parseIntent() {
        Intent intent = getIntent();
        if(intent != null) {
            mUrl = intent.getStringExtra(EXTRA_URL);
            mTitle = intent.getStringExtra(EXTRA_TITLE);
        }
        GLog.d(TAG, "parseIntent, url=" + mUrl);
    }

    private void openUrlByExternal(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void setWebTitle(String title) {
        setToolbarTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.copy_url:
                TextKit.copyToClipboard(this, mUrl);
                break;
            case R.id.open_by_external_browser:
                openUrlByExternal(mUrl);
                break;
            case R.id.refresh:
                if(!mIsRefreshing) {
                    mRefreshItem = item;
                    refresh();
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_activity,menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    public static Intent launchWebActivity(@NonNull String url, @Nullable String title, @NonNull Activity startActivity) {
        Intent intent = new Intent(startActivity, WebActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_TITLE, title);
        return intent;
    }
}
