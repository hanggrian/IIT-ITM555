package com.example.pokemon.ui;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.pokemon.Contexts;

public class RefreshLayout extends SwipeRefreshLayout {
    public RefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public RefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeColors(Contexts.getAttrColor(context, android.R.attr.colorAccent));
    }
}
