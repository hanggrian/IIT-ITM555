package com.example.tempconverter1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

/**
 * The first and only activity of this application. It uses the deprecated XML {@code OnClick}
 * binding into {@link #calculate(View)} to update controls based on user input.
 */
public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText input;
    RadioButton fahrenheitRadio;
    RadioButton celsiusRadio;
    ImageView image;
    TextView titleText;
    TextView subtitleText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        input = findViewById(R.id.input);
        fahrenheitRadio = findViewById(R.id.fahrenheitRadio);
        celsiusRadio = findViewById(R.id.celsiusRadio);
        image = findViewById(R.id.image);
        titleText = findViewById(R.id.titleText);
        subtitleText = findViewById(R.id.subtitleText);

        setSupportActionBar(toolbar);

        input.addTextChangedListener(
            new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    calculate(fahrenheitRadio.isChecked() ? fahrenheitRadio : celsiusRadio);
                }

                @Override
                public void afterTextChanged(Editable editable) {}
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
        }
        return super.onOptionsItemSelected(item);
    }

    public void calculate(View view) {
        String s = input.getText().toString();
        if (TextUtils.isEmpty(s) || s.equals("-")) {
            image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.hi));
            image.clearColorFilter();
            titleText.setText(null);
            subtitleText.setText(R.string.desc_welcome);
            return;
        }

        Temperature temperature =
            new Temperature(
                Double.parseDouble(s),
                view == fahrenheitRadio
                    ? Temperature.Unit.FAHRENHEIT
                    : Temperature.Unit.CELSIUS
            );
        temperature.convert();

        String title = temperature.toString();
        Season season = temperature.getSeason();
        int color = season.getColor(this);
        image.setImageDrawable(season.getLogo(this));
        image.setColorFilter(color);
        titleText.setText(title);
        titleText.setTextColor(color);
        subtitleText.setText(season.getDescription(this));
    }
}
