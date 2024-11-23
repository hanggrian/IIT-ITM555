package com.example.pokemon.ui;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.pokemon.R;
import com.example.pokemon.db.PokemonDatabase;

public abstract class NextActivity extends AppCompatActivity {
    protected Toolbar toolbar;

    protected PokemonDatabase db;

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutRes());
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        db = PokemonDatabase.from(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
