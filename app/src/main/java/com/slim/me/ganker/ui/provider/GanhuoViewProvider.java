package com.slim.me.ganker.ui.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slim.me.ganker.R;
import com.slim.me.ganker.data.entity.Gank;
import com.slim.me.ganker.util.TextKit;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        holder.desc.setText(TextKit.generate(gank, color));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.desc)
        TextView desc;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            desc.setMovementMethod(new LinkMovementMethod());
        }
    }

}
