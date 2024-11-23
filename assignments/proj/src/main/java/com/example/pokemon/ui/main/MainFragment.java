package com.example.pokemon.ui.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.example.pokemon.R;

public abstract class MainFragment extends Fragment implements Toolbar.OnMenuItemClickListener {
    private static final int NO_MENU = 1;

    protected Toolbar toolbar;

    @MenuRes
    protected int getMenuRes() {
        return NO_MENU;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        toolbar = view.findViewById(R.id.toolbar);

        if (getMenuRes() == NO_MENU) {
            return;
        }
        toolbar.inflateMenu(getMenuRes());
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @NonNull
    public final MainViewModel getViewModel() {
        return ((MainActivity) requireActivity()).viewModel;
    }
}
