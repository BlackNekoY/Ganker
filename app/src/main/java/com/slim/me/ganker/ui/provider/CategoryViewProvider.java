package com.slim.me.ganker.ui.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slim.me.ganker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by Slim on 2017/2/25.
 */

public class CategoryViewProvider extends ItemViewProvider<CategoryViewProvider.Category, CategoryViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.gank_category_layout, null));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Category category) {
        holder.category.setText(category.category);
    }

    public static class Category {
        public final String category;

        public Category(String category) {
            this.category = category;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category)
        TextView category;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
