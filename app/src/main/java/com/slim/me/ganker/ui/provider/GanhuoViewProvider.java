package com.slim.me.ganker.ui.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slim.me.ganker.R;
import com.slim.me.ganker.data.entity.Gank;
import com.slim.me.ganker.ui.event.JumpToWebEvent;
import com.slim.me.ganker.util.GLog;
import com.slim.me.ganker.util.TextKit;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by Slim on 2017/2/25.
 */
public class GanhuoViewProvider extends ItemViewProvider<Gank, GanhuoViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.gank_ganhuo_layout, null));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Gank gank) {
        int color = holder.itemView.getContext().getResources().getColor(R.color.link_color_blue);
        holder.desc.setText(TextKit.generate(gank, color, false));
        holder.gank = gank;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.desc)
        TextView desc;

        public Gank gank;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnTouch(R.id.desc)
        public boolean onTouch(View view, MotionEvent event) {
            GLog.d("ontouch", "action:" + event.getAction());
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    int color1 = itemView.getContext().getResources().getColor(R.color.link_color_blue);
                    desc.setText(TextKit.generate(gank, color1, true));
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    int color2 = itemView.getContext().getResources().getColor(R.color.link_color_blue);
                    desc.setText(TextKit.generate(gank, color2, false));
                    break;
            }
            return false;
        }

        @OnClick(R.id.desc)
        public void onLinkClick() {
            JumpToWebEvent event = new JumpToWebEvent(gank.url, gank.desc);
            EventBus.getDefault().post(event);
        }

    }

}
