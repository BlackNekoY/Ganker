package com.slim.me.ganker.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.slim.me.ganker.R;
import com.slim.me.ganker.ui.presenter.PhotoPresenter;
import com.slim.me.ganker.ui.view.IPhotoView;
import com.slim.me.ganker.util.GLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Slim on 2017/2/26.
 */
public class PhotoActivity extends BaseActivity implements IPhotoView {

    public static final String TAG = "PhotoActivity";
    public static final String EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL";

    @BindView(R.id.photo_view) PhotoView photoView;

    @BindView(R.id.root) CoordinatorLayout root;

    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @BindView(R.id.save_file) ImageView save;

    private PhotoViewAttacher mAttacher;
    private String mImageUrl;
    private String mImageName;
    private Bitmap mBitmap;

    private PhotoPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ButterKnife.bind(this);
        parseIntent();

        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.material_purple_500), android.graphics.PorterDuff.Mode.MULTIPLY);

        Glide.with(this)
                .load(mImageUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if(resource instanceof GlideBitmapDrawable) {
                            mBitmap = ((GlideBitmapDrawable) resource).getBitmap();
                        }
                        mAttacher.update();
                        return false;
                    }
                })
                .into(photoView);
        mAttacher = new PhotoViewAttacher(photoView);

        mPresenter = new PhotoPresenter(this);
    }

    private void parseIntent() {
        mImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        if (TextUtils.isEmpty(mImageUrl)) {
            GLog.e(TAG, "ImageUrl is null, finish.");
            finish();
            return;
        }

        int lastIndex = mImageUrl.lastIndexOf("/");
        if(lastIndex != -1 && lastIndex >= 0) {
            lastIndex += 1;
            mImageName = mImageUrl.substring(lastIndex);
        }
        if(TextUtils.isEmpty(mImageName)) {
            mImageName = mImageUrl;
        }
        GLog.d(TAG, "ImageName:" + mImageName);
    }

    @Override
    protected void unsubscribePresenterSubscription() {
        mPresenter.unSubscribeAllSubscription();
    }

    @Override
    public void showSnack(@StringRes int resId, int duration) {
        Snackbar.make(root, resId, duration).show();
    }

    @Override
    public void showSnack(String text, int duration) {
        Snackbar.make(root, text, duration).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnLongClick(R.id.photo_view)
    public boolean onImageLongClick() {
        return true;
    }

    @OnClick(R.id.save_file)
    public void savePhoto() {
        if(mBitmap == null) {
            GLog.e(TAG, "bitmap is null, can't savePhoto.");
            return;
        }
        mPresenter.savePhoto(mBitmap, mImageName);
    }

    @Override
    public void setProgressVisible(boolean isVisible) {
        // 此处set GONE 会导致PhotoView的缩放比率复位.....
        if(isVisible) {
            save.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }else {
            save.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public static Intent launchPhotoActivity(@NonNull String imageUrl, Activity startActivity) {
        Intent intent = new Intent(startActivity, PhotoActivity.class);
        intent.putExtra(EXTRA_IMAGE_URL, imageUrl);
        return intent;
    }
}
