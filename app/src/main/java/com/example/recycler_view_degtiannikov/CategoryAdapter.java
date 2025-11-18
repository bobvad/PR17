package com.example.recycler_view_degtiannikov;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private OnClickInterface OnClickInterface;
    private final LayoutInflater Inflater;
    private final List<Category> Categories;
    private Drawable BackgroundSelect;

    CategoryAdapter(Context context, List<Category> categories, OnClickInterface onClickInterface) {
        this.Inflater = LayoutInflater.from(context);
        this.Categories = categories;
        BackgroundSelect = ContextCompat.getDrawable(context, R.drawable.item_category_background_select);
        this.OnClickInterface = onClickInterface;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = Inflater.inflate(R.layout.item_category, parent, false); // создаём View из свёрстанного элемента
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        Category category = Categories.get(position);
        holder.tvName.setText(category.Name);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OnClickInterface.setClick(holder.parent, position);
            }
        });
        if (category.Active) {
            holder.parent.setBackground(BackgroundSelect);
            holder.tvName.setTextColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return Categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ConstraintLayout parent;
        final TextView tvName;

        ViewHolder(View view) {
            super(view);
            parent = view.findViewById(R.id.parent);
            tvName = view.findViewById(R.id.tv_name);
        }
    }

    public interface OnClickInterface {
        void setClick(View view, int position);
    }
}
