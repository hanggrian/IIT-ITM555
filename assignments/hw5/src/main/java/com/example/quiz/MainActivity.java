package com.example.quiz;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;

/**
 * A single screen displaying quiz question, answer toggles and rating bar representing user's
 * progress. At any point during the quiz, user may return to the initial point by selecting the
 * <b>Reset</b> menu item.
 */
public class MainActivity extends AppCompatActivity {
    private static final long LOADING_DELAY = 1500L;
    static final Map<String, Integer> RESULT_MAP = new HashMap<>();

    static {
        RESULT_MAP.put("Android's current stable OS release is Android 14.", R.id.trueRadio);
        RESULT_MAP.put(
            "An AsyncTask is tied to the life cycle of the Activity that contains it.",
            R.id.falseRadio
        );
        RESULT_MAP.put(
            "The last callback in the lifecycle of an activity is onDestroy()",
            R.id.trueRadio
        );
        RESULT_MAP.put(
            "To collapse / expand items use the Code -> Folding menu in AS.",
            R.id.trueRadio
        );
        RESULT_MAP.put("You cannot start an Activity with an Intent.", R.id.falseRadio);
    }

    CollapsingToolbarLayout toolbarLayout;
    Toolbar toolbar;
    ProgressBar progress;

    TextView text;
    RadioGroup radioGroup;
    Button displayButton;
    RatingBar rating;
    FloatingActionButton action;

    MainViewModel viewModel;
    PapademasApi api;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbarLayout = findViewById(R.id.toolbarLayout);
        toolbar = findViewById(R.id.toolbar);
        progress = findViewById(R.id.progress);
        text = findViewById(R.id.text);
        radioGroup = findViewById(R.id.radioGroup);
        displayButton = findViewById(R.id.displayButton);
        rating = findViewById(R.id.rating);
        action = findViewById(R.id.action);

        setSupportActionBar(toolbar);

        api = PapademasApi.create();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.stateData.observe(
            this,
            state -> {
                switch (state) {
                    case LOADING:
                        reset();
                        break;
                    case ANSWERING:
                        displayButton.setVisibility(View.VISIBLE);
                        action.setVisibility(View.INVISIBLE);
                        radioGroup.clearCheck();
                        break;
                    case ANSWERED:
                        displayButton.setVisibility(View.INVISIBLE);
                        action.setVisibility(View.VISIBLE);
                        break;
                    case FINISHED:
                        displayButton.setVisibility(View.INVISIBLE);
                        action.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        );
        viewModel.questionIndexData.observe(
            this,
            questionIndex -> {
                if (viewModel.questions.isEmpty()) {
                    return;
                }
                progress.setProgress(questionIndex);
                toolbarLayout.setTitle(getString(R.string.quiz_d, questionIndex + 1));
                text.setText(viewModel.questions.get(questionIndex));
            }
        );
        viewModel.answerTallyData.observe(
            this,
            answerTally -> rating.setRating((float) answerTally.first / answerTally.second * 5f)
        );

        displayButton.setOnClickListener(
            v -> {
                boolean isCorrect =
                    Objects.requireNonNull(RESULT_MAP.get(text.getText().toString()))
                        == radioGroup.getCheckedRadioButtonId();
                viewModel.updateAnswerTally(isCorrect);
                viewModel.stateData.setValue(
                    Objects.requireNonNull(viewModel.questionIndexData.getValue())
                        == RESULT_MAP.size() - 1
                        ? State.FINISHED
                        : State.ANSWERED
                );

                Toast
                    .makeText(
                        MainActivity.this,
                        isCorrect ? R.string.correct_ : R.string.wrong_,
                        Toast.LENGTH_SHORT
                    ).show();
            }
        );
        radioGroup.setOnCheckedChangeListener(
            (group, checkedId) -> displayButton.setEnabled(checkedId != -1)
        );
        action.setOnClickListener(
            v -> {
                viewModel.questionIndexData.setValue(
                    Objects.requireNonNull(viewModel.questionIndexData.getValue()) + 1
                );
                viewModel.stateData.setValue(State.ANSWERING);
            }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            new AboutDialog().show(getSupportFragmentManager(), AboutDialog.TAG);
        } else if (item.getItemId() == R.id.reset) {
            new ConfirmDialog().show(getSupportFragmentManager(), ConfirmDialog.TAG);
        }
        return super.onOptionsItemSelected(item);
    }

    private void reset() {
        if (loadingDialog != null) {
            return;
        }
        loadingDialog = new LoadingDialog();
        loadingDialog.show(getSupportFragmentManager(), LoadingDialog.TAG);
        Executors.newSingleThreadExecutor().execute(
            () -> {
                try {
                    viewModel.questions.clear();
                    viewModel.questions.addAll(api.getRandomizedQuestions());
                    new Handler(Looper.getMainLooper())
                        .postDelayed(
                            () -> {
                                loadingDialog.dismiss();
                                loadingDialog = null;

                                viewModel.stateData.setValue(State.ANSWERING);
                                viewModel.questionIndexData.setValue(0);
                                viewModel.answerTallyData.setValue(new Pair<>(0, 0));
                            },
                            LOADING_DELAY
                        );
                } catch (IOException e) {
                    loadingDialog.dismiss();
                    loadingDialog = null;

                    String message = e.getMessage();
                    if (message == null) {
                        message = "Unknown error.";
                    }
                    Snackbar
                        .make(toolbarLayout, message, Snackbar.LENGTH_LONG)
                        .show();
                }
            }
        );
    }

    public static class ConfirmDialog extends DialogFragment {
        public static final String TAG = "ConfirmDialog";

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.reset)
                .setMessage(getString(R.string.are_you_sure_))
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {})
                .setPositiveButton(
                    android.R.string.ok,
                    (dialog, which) -> ((MainActivity) requireActivity()).reset()
                ).create();
        }
    }

    public static class LoadingDialog extends DialogFragment {
        public static final String TAG = "LoadingDialog";

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.loading)
                .setMessage(getString(R.string.please_wait_))
                .create();
        }
    }
}
