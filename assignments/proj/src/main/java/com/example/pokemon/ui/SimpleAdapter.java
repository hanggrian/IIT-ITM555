package com.example.pokemon.ui;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public abstract class SimpleAdapter<T, VH extends RecyclerView.ViewHolder> extends
    RecyclerView.Adapter<VH> {
    protected final Context context;
    protected final List<T> list;

    public SimpleAdapter(@NonNull Context context, @NonNull List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public final int getItemCount() {
        return list.size();
    }

    protected final T getItem(int position) {
        return list.get(position);
    }
}
