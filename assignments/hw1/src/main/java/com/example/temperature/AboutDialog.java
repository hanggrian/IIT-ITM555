package com.example.temperature;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AboutDialog extends DialogFragment {
    public static final String TAG = "AboutDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
            .setTitle("About")
            .setMessage("A simple temperature converter app.")
            .setPositiveButton(android.R.string.ok, (dialog, which) -> {})
            .create();
    }
}
