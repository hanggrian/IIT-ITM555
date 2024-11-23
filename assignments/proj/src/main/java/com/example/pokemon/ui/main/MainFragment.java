package com.example.pokemon.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class MainFragment extends Fragment {
    @NonNull
    protected final MainViewModel getViewModel() {
        return ((MainActivity) requireActivity()).viewModel;
    }
}
