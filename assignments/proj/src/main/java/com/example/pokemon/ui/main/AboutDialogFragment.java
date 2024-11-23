package com.example.pokemon.ui.main;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.pokemon.Contexts;
import com.example.pokemon.R;

/**
 * A simple dialog describing what the application does. This dialog must be attached to a
 * {@link DialogFragment}.
 */
public class AboutDialogFragment extends DialogFragment {
    public static final String TAG = "AboutDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
            .setTitle(R.string.about)
            .setMessage(R.string.app_about)
            .setNegativeButton(
                R.string.visit_github,
                (dialog, which) ->
                    Contexts.sendViewIntent(
                        requireContext(),
                        "https://github.com/hanggrian/IIT-ITM555"
                    )
            ).setPositiveButton(android.R.string.ok, (dialog, which) -> {})
            .create();
    }
}