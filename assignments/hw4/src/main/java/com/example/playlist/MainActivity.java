package com.example.playlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.playlist.data.MusicCatalog;
import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.Executors;

/**
 * Initial point of the application. When a preference of key {@link #USERNAME_KEY} is not found,
 * the activity will destroy itself and redirect to login screen for registration.
 */
public class MainActivity extends AppCompatActivity {
    public static final String USERNAME_KEY = "username";

    Toolbar toolbar;
    SwipeRefreshLayout refreshLayout;
    RecyclerView recycler;

    MusicAdapter adapter;
    PapademasApi api;

    private boolean isRefreshing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        refreshLayout = findViewById(R.id.refreshLayout);
        recycler = findViewById(R.id.recycler);

        setSupportActionBar(toolbar);

        api = PapademasApi.create();
        adapter = new MusicAdapter(this);

        recycler.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this::refresh);
        refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String username =
            PreferenceManager
                .getDefaultSharedPreferences(this)
                .getString(USERNAME_KEY, null);
        if (username != null) {
            toolbar.setSubtitle(username);
            return;
        }
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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
        } else if (item.getItemId() == R.id.refresh) {
            refreshLayout.setRefreshing(true);
            refresh();
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        Executors.newSingleThreadExecutor().execute(
            () -> {
                try {
                    MusicCatalog catalog =
                        api
                            .getMusicCatalog()
                            .execute()
                            .body();
                    new Handler(Looper.getMainLooper()).post(
                        () -> {
                            refreshLayout.setRefreshing(false);
                            isRefreshing = false;

                            adapter.replaceAll(
                                catalog != null
                                    ? catalog.getBluesy()
                                    : Collections.emptyList()
                            );
                        }
                    );
                } catch (IOException e) {
                    refreshLayout.setRefreshing(false);
                    isRefreshing = false;

                    String message = e.getMessage();
                    if (message == null) {
                        message = "Unknown error.";
                    }
                    Snackbar
                        .make(recycler, message, Snackbar.LENGTH_LONG)
                        .show();
                }
            }
        );
    }
}
