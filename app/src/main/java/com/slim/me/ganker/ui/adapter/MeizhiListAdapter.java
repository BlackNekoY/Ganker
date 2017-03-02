package com.slim.me.ganker.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.slim.me.ganker.R;
import com.slim.me.ganker.data.entity.Meizhi;
import com.slim.me.ganker.ui.widget.RatioImageView;
import com.slim.me.ganker.util.GLog;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Slim on 2017/2/19.
 */
public class MeizhiListAdapter extends RecyclerView.Adapter<MeizhiListAdapter.MeizhiItemViewHolder>{

    public static final String TAG = "MeizhiListAdapter";
    private final Context mContext;

    private List<Meizhi> mMeizhis = new ArrayList<>();
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");

    public MeizhiListAdapter(Context context,List<Meizhi> meizhis) {
        mContext = context;
        if(meizhis != null) {
            mMeizhis.addAll(meizhis);
        }
    }

    @Override
    public MeizhiItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meizhi_item, null);
        return new MeizhiItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MeizhiItemViewHolder holder, int position) {
        Meizhi meizhi = mMeizhis.get(position);
        holder.time.setText(mFormat.format(meizhi.publishedAt));

        Glide.with(mContext)
                .load(meizhi.url)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.photo);
        holder.meizhi = meizhi;
    }

    @Override
    public int getItemCount() {
        return mMeizhis.size();
    }

    public void updateMeizhiList(List<Meizhi> meizhiList) {
        mMeizhis.clear();
        if(meizhiList != null) {
            mMeizhis.addAll(meizhiList);
        }
        notifyDataSetChanged();
    }

    public void updateMeizhiByPosition(@NonNull Meizhi meizhi, int position) {
        int size = mMeizhis.size();
        if(position >= 0 && position < size) {
            mMeizhis.set(position, meizhi);
            notifyItemChanged(position);
        }else {
            GLog.e(TAG, "error position:" + position + ", size:" + size);
        }
    }


    static class MeizhiItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.meizhi)
        RatioImageView photo;

        @BindView(R.id.time)
        TextView time;

        Meizhi meizhi;

        public MeizhiItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            photo.setOriginalSize(50,75);
        }

        @OnClick(R.id.meizhi)
        public void onMeizhiClick() {
            EventBus.getDefault().post(new MeizhiClickEvent(meizhi));
        }
    }

    public static class MeizhiClickEvent {
        public final Meizhi meizhi;

        MeizhiClickEvent(Meizhi meizhi) {
            this.meizhi = meizhi;
        }
    }

}
