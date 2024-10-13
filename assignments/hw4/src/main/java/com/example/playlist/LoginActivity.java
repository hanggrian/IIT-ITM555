package com.example.playlist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;
import com.google.android.material.snackbar.Snackbar;

/**
 * A small activity that validates user input, then stores credentials in shared prefs.
 * Specifically, the password must match {@link #DEFAULT_PASSWORD} as described in
 * {@link ForgotPasswordDialog}.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String DEFAULT_PASSWORD = "itmd4555";
    private static final long WAIT_DURATION = 5000L;

    Toolbar toolbar;
    ProgressBar progress;
    EditText usernameEdit;
    EditText passwordEdit;
    Button forgotPasswordButton;
    Button aboutButton;
    Button proceedButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        toolbar = findViewById(R.id.toolbar);
        progress = findViewById(R.id.progress);
        usernameEdit = findViewById(R.id.usernameEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        aboutButton = findViewById(R.id.aboutButton);
        proceedButton = findViewById(R.id.proceedButton);

        setSupportActionBar(toolbar);

        forgotPasswordButton.setOnClickListener(
            v ->
                new ForgotPasswordDialog()
                    .show(getSupportFragmentManager(), ForgotPasswordDialog.TAG)
        );
        aboutButton.setOnClickListener(
            v -> new AboutDialog().show(getSupportFragmentManager(), AboutDialog.TAG)
        );
        proceedButton.setOnClickListener(
            v -> {
                enableControls(false);
                new Handler(Looper.getMainLooper()).postDelayed(
                    () -> {
                        enableControls(true);
                        if (!passwordEdit.getText().toString().equals(DEFAULT_PASSWORD)) {
                            Snackbar
                                .make(v, R.string.invalid_credentials_desc, Snackbar.LENGTH_LONG)
                                .show();
                            return;
                        }

                        Toast
                            .makeText(this, R.string.redirecting_, Toast.LENGTH_LONG)
                            .show();

                        SharedPreferences.Editor editor =
                            PreferenceManager
                                .getDefaultSharedPreferences(this)
                                .edit();
                        editor.putString(
                            MainActivity.USERNAME_KEY,
                            usernameEdit.getText().toString()
                        );
                        editor.apply();

                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    },
                    WAIT_DURATION
                );
            }
        );

        TextWatcher watcher =
            new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    proceedButton.setEnabled(
                        !usernameEdit.getText().toString().contains(" ")
                            && usernameEdit.getText().length() > 3
                            && passwordEdit.getText().length() > 0
                    );
                }

                @Override
                public void afterTextChanged(Editable s) {}
            };
        usernameEdit.addTextChangedListener(watcher);
        passwordEdit.addTextChangedListener(watcher);
    }

    private void enableControls(boolean isEnable) {
        progress.setVisibility(isEnable ? View.GONE : View.VISIBLE);
        usernameEdit.setEnabled(isEnable);
        passwordEdit.setEnabled(isEnable);
        proceedButton.setEnabled(isEnable);

        if (!isEnable) {
            ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(proceedButton.getWindowToken(), 0);
        }
    }

    public static class ForgotPasswordDialog extends DialogFragment {
        public static final String TAG = "ForgotPasswordDialog";

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.forgot_password)
                .setMessage(getString(R.string.forgot_password_desc, DEFAULT_PASSWORD))
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {})
                .create();
        }
    }
}
