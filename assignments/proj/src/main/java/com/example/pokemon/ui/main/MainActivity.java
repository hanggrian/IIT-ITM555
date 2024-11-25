package com.example.pokemon.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.example.pokemon.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final long DURATION_SPLASH = 3000L;

    public static final String EXTRA_MEMBER = "MainActivity#EXTRA_MEMBER";

    FragmentContainerView container;
    BottomNavigationView navigation;

    MainViewModel viewModel;
    boolean isReady = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);
        navigation = findViewById(R.id.navigation);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        NavController navController =
            Objects
                .requireNonNull(
                    (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.container)
                ).getNavController();
        NavigationUI.setupWithNavController(navigation, navController);
        navigation.setOnItemSelectedListener(
            item -> {
                int fromId = navigation.getSelectedItemId();
                int toId = item.getItemId();
                if (fromId == toId) {
                    return false;
                }
                if (fromId == R.id.monsterItem) {
                    navController.navigate(
                        toId == R.id.moveItem
                            ? R.id.monsterToMoveAction
                            : R.id.monsterToRosterAction
                    );
                } else if (fromId == R.id.moveItem) {
                    navController.navigate(
                        toId == R.id.monsterItem
                            ? R.id.moveToMonsterAction
                            : R.id.moveToRosterAction
                    );
                } else if (fromId == R.id.rosterItem) {
                    navController.navigate(
                        toId == R.id.monsterItem
                            ? R.id.rosterToMonsterAction
                            : R.id.rosterToMoveAction
                    );
                }
                return true;
            }
        );

        View content = findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnPreDrawListener(
            new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (isReady) {
                        content.getViewTreeObserver().removeOnPreDrawListener(this);
                    }
                    return isReady;
                }
            }
        );
        new Handler(Looper.getMainLooper())
            .postDelayed(() -> isReady = true, DURATION_SPLASH);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fragment_roster, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.aboutItem) {
            new AboutDialogFragment().show(getSupportFragmentManager(), AboutDialogFragment.TAG);
            return true;
        }
        return false;
    }
}
