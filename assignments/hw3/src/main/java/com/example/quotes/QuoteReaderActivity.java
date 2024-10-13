package com.example.quotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.splashscreen.SplashScreen;
import java.util.List;

/**
 * The first activity that is shown after splash screen displayed using
 * {@link SplashScreen#installSplashScreen(android.app.Activity)}. It contains a list control of
 * Steve Jobs' quotes.
 */
public class QuoteReaderActivity extends AppCompatActivity {
    private static final long DURATION_SPLASH = 5000L;

    View content;
    Toolbar toolbar;
    ListView list;

    private DataSource source;
    private boolean isReady;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quote_reader);
        content = findViewById(android.R.id.content);
        toolbar = findViewById(R.id.toolbar);
        list = findViewById(R.id.list);

        setSupportActionBar(toolbar);

        source = new DataSource();

        list.setAdapter(new QuoteAdapter(this, source));
        list.setOnItemClickListener(
            (parent, view, position, id) -> {
                Intent intent = new Intent(QuoteReaderActivity.this, QuoteDetailActivity.class);
                intent.putExtra(QuoteDetailActivity.EXTRA_POSITION, position);
                intent.putExtra(QuoteDetailActivity.EXTRA_DATA_SOURCE, source);
                startActivity(intent);
            }
        );

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
        getMenuInflater().inflate(R.menu.activity_quote_reader, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            new AboutDialog().show(getSupportFragmentManager(), AboutDialog.TAG);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * List adapter in the main activity.
     */
    public static class QuoteAdapter extends BaseAdapter {
        private final LayoutInflater inflater;
        private final List<Integer> thumbnails;
        private final List<Integer> quotes;

        /**
         * Create a new adapter with defined collections.
         *
         * @param context active activity.
         * @param source list item content.
         */
        public QuoteAdapter(@NonNull Context context, @NonNull DataSource source) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            thumbnails = source.getPhotoThumbnails();
            quotes = source.getQuotes();
        }

        @Override
        public int getCount() {
            return thumbnails.size();
        }

        @Override
        public Object getItem(int position) {
            return quotes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_quote, parent, false);
            }

            ImageView thumbnail = convertView.findViewById(R.id.image);
            TextView quote = convertView.findViewById(R.id.text);

            thumbnail.setImageResource(thumbnails.get(position));
            quote.setText(quotes.get(position));

            return convertView;
        }
    }
}
