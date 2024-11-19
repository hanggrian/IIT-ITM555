package com.example.marks;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.example.marks.db.MarkDatabase;
import com.example.marks.db.schema.Mark;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.Objects;

/**
 * The only activity in the application, responsible for displaying items with {@link #adapter} and
 * connecting to database in {@link #viewModel}.
 */
public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText idEdit;
    EditText nameEdit;
    EditText valueEdit;
    RecyclerView recycler;
    View emptyView;
    FloatingActionButton action;

    MainViewModel viewModel;
    MarkAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        idEdit = findViewById(R.id.idEdit);
        nameEdit = findViewById(R.id.nameEdit);
        valueEdit = findViewById(R.id.valueEdit);
        recycler = findViewById(R.id.recycler);
        emptyView = findViewById(R.id.emptyView);
        action = findViewById(R.id.action);

        setSupportActionBar(toolbar);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.db = MarkDatabase.from(this);
        adapter = new MarkAdapter(viewModel.marks);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                onChanged();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                onChanged();
            }

            @Override
            public void onChanged() {
                if (viewModel.marks.isEmpty()) {
                    recycler.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    return;
                }
                recycler.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
        });

        Tasks.executeResult(
            () -> viewModel.db.marks().getAll(),
            list -> {
                viewModel.marks.clear();
                viewModel.marks.addAll(list);
                adapter.notifyDataSetChanged();
            }
        );

        recycler.setAdapter(adapter);

        action.setOnClickListener(
            v -> {
                int id = Integer.parseInt(idEdit.getText().toString().trim());
                Tasks.executeResult(
                    () -> {
                        if (!viewModel.db.marks().isExist(id)) {
                            Mark mark =
                                new Mark.Builder()
                                    .id(id)
                                    .name(nameEdit.getText().toString().trim())
                                    .value(valueEdit.getText().toString().trim().charAt(0))
                                    .build();
                            viewModel.db.marks().insertAll(mark);
                            return mark;
                        }
                        Mark mark = viewModel.db.marks().findById(String.valueOf(id));
                        mark.setName(nameEdit.getText().toString().trim());
                        mark.setValue(valueEdit.getText().toString().trim().charAt(0));
                        viewModel.db.marks().update(mark);
                        return mark;
                    }, (mark) -> {
                        int index = viewModel.marks.indexOf(mark);
                        if (index > -1) {
                            viewModel.marks.set(index, mark);
                            adapter.notifyItemChanged(index);
                        } else {
                            if (viewModel.marks.add(mark)) {
                                adapter.notifyItemInserted(viewModel.marks.size() - 1);
                            }
                        }
                        idEdit.setText("");
                        nameEdit.setText("");
                        valueEdit.setText("");
                        idEdit.requestFocus();
                    }
                );
            }
        );

        TextWatcher watcher =
            new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    action.setVisibility(
                        TextUtils.isEmpty(idEdit.getText())
                            || !Mark.PATTERN_NAME.matcher(nameEdit.getText()).matches()
                            || !Mark.PATTERN_VALUE.matcher(valueEdit.getText()).matches()
                            ? View.GONE
                            : View.VISIBLE
                    );
                }

                @Override
                public void afterTextChanged(Editable s) {}
            };
        idEdit.addTextChangedListener(watcher);
        nameEdit.addTextChangedListener(watcher);
        valueEdit.addTextChangedListener(watcher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Objects.requireNonNull(viewModel.db).close();
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

    public static class ConfirmDialog extends DialogFragment {
        public static final String TAG = "MainActivity.ConfirmDialog";

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.confirm)
                .setMessage(getString(R.string.dialog_clear_desc))
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {})
                .setPositiveButton(
                    android.R.string.ok,
                    (dialog, which) -> {
                        MainActivity activity = (MainActivity) requireActivity();
                        Tasks.execute(
                            () -> Objects.requireNonNull(activity.viewModel.db).marks().deleteAll(),
                            () ->
                                Snackbar
                                    .make(
                                        activity.action,
                                        R.string.menu_reset_desc,
                                        Snackbar.LENGTH_INDEFINITE
                                    ).setAction(
                                        android.R.string.ok,
                                        v -> {
                                            int size = activity.viewModel.marks.size();
                                            activity.viewModel.marks.clear();
                                            activity.adapter.notifyItemRangeRemoved(0, size);
                                        }
                                    ).show()
                        );
                    }
                ).create();
        }
    }
}
