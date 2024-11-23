package com.example.pokemon.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.pokemon.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    FragmentContainerView container;
    BottomNavigationView navigation;

    MainViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        container = findViewById(R.id.container);
        navigation = findViewById(R.id.navigation);

        setSupportActionBar(toolbar);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        NavController navController =
            Objects
                .requireNonNull(
                    (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.container)
                ).getNavController();
        NavigationUI.setupActionBarWithNavController(
            this,
            navController,
            new AppBarConfiguration.Builder(
                R.id.monsterFragment,
                R.id.moveFragment,
                R.id.rosterFragment
            ).build()
        );
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_top, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.aboutItem) {
            new AboutDialogFragment().show(getSupportFragmentManager(), AboutDialogFragment.TAG);
        }
        return super.onOptionsItemSelected(item);
    }
}
