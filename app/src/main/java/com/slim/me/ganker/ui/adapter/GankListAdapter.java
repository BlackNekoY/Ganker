package com.slim.me.ganker.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slim.me.ganker.R;
import com.slim.me.ganker.data.entity.Gank;
import com.slim.me.ganker.ui.event.JumpToWebEvent;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by slimxu on 2017/3/3.
 */
public class GankListAdapter extends RecyclerView.Adapter<GankListAdapter.GankItemViewHolder> {

    private Context mContext;
    private List<Gank> mGanks = new ArrayList<>();
    public GankListAdapter(Context context, List<Gank> ganks) {
        mContext = context;
        if (ganks != null) {
            mGanks.addAll(ganks);
        }
    }

    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy年MM月dd日");

    public void updateGankList(List<Gank> ganks) {
        mGanks.clear();
        if (ganks != null) {
            mGanks.addAll(ganks);
        }
        notifyDataSetChanged();
    }

    @Override
    public GankItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GankItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.gank_item, null));
    }

    @Override
    public void onBindViewHolder(GankItemViewHolder holder, int position) {
        Gank gank = mGanks.get(position);
        holder.desc.setText(gank.desc);
        holder.time.setText(mFormat.format(gank.publishedAt));
        holder.author.setText(TextUtils.isEmpty(gank.who) ? "（none）" : "（" + gank.who + "）");
        holder.gank = gank;
    }

    @Override
    public int getItemCount() {
        return mGanks.size();
    }

    static class GankItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.author)
        TextView author;

        Gank gank;


        public GankItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.card)
        public void onCardClick() {
            JumpToWebEvent event = new JumpToWebEvent(gank.url, gank.desc);
            EventBus.getDefault().post(event);
        }
    }
}
