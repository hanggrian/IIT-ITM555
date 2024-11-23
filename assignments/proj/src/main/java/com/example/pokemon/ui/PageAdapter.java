package com.example.pokemon.ui;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PageAdapter<T, VH extends RecyclerView.ViewHolder> extends
    PagingDataAdapter<T, VH> {
    protected final Context context;

    public PageAdapter(@NonNull Context context, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        this.context = context;
    }
}
