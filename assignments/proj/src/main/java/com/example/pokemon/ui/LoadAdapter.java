package com.example.pokemon.ui;

import androidx.annotation.NonNull;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

public abstract class LoadAdapter<T extends RecyclerView.ViewHolder> extends LoadStateAdapter<T> {
    protected final Runnable retryAction;

    public LoadAdapter(@NonNull Runnable retryAction) {
        this.retryAction = retryAction;
    }
}
