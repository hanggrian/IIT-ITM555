package com.example.quotes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.widget.NestedScrollView;
import androidx.palette.graphics.Palette;
import androidx.palette.graphics.Target;
import com.google.android.material.appbar.AppBarLayout;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * The next activity that receives extra data from {@link QuoteReaderActivity}. Background and text
 * colors are dynamically-generated from {@link Palette.Builder#generate()}.
 */
public class QuoteDetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_DATA_SOURCE = "data_source";

    AppBarLayout appbar;
    Toolbar toolbar;
    NestedScrollView scrollView;
    ImageView image;
    TextView text;

    private Palette palette;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_detail);
        appbar = findViewById(R.id.appbar);
        toolbar = findViewById(R.id.toolbar);
        scrollView = findViewById(R.id.scroll_view);
        image = findViewById(R.id.image);
        text = findViewById(R.id.text);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        int position = intent.getIntExtra(EXTRA_POSITION, 0);
        DataSource source =
            Objects.requireNonNull((DataSource) intent.getSerializableExtra(EXTRA_DATA_SOURCE));
        toolbar.setSubtitle("#" + position);
        image.setImageResource(source.getPhotos().get(position));
        text.setText(getString(source.getQuotes().get(position)));

        palette = Palette.from(((BitmapDrawable) image.getDrawable()).getBitmap()).generate();
        setPaletteColor(
            Target.DARK_VIBRANT,
            color -> {
                appbar.setBackgroundColor(color);

                Window window = getWindow();
                window.setStatusBarColor(color);
                window.setNavigationBarColor(color);
            }
        );
        setPaletteColor(Target.DARK_MUTED, color -> scrollView.setBackgroundColor(color));

        setPaletteColor(Target.VIBRANT, color -> toolbar.setTitleTextColor(color));
        setPaletteColor(Target.LIGHT_VIBRANT, color -> toolbar.setSubtitleTextColor(color));
        setPaletteColor(Target.MUTED, color -> text.setTextColor(color));
    }

    private void setPaletteColor(@NonNull Target target, @NonNull Consumer<Integer> consumer) {
        int color = palette.getColorForTarget(target, Color.TRANSPARENT);
        if (color != Color.TRANSPARENT) {
            consumer.accept(color);
        }
    }
}
