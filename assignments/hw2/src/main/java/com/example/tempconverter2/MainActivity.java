package com.example.tempconverter2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.slider.Slider;

/**
 * The main activity of Temperature Converter 2 sample application. Unlike the first iteration of
 * the app that accepts user text input, the second app converts temperature value using a slider.
 * Additionally, there is a stub view displaying forecast when user selects the check box.
 */
public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RelativeLayout layout;
    TextView text1;
    TextView text2;
    Slider slider;
    CheckBox check;

    ViewStub stub;
    RecyclerView list;
    Button button;

    private MainViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        layout = findViewById(R.id.layout);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        stub = findViewById(R.id.stub);
        check = findViewById(R.id.check);
        slider = findViewById(R.id.slider);

        setSupportActionBar(toolbar);

        View inflated = stub.inflate();
        stub.setVisibility(View.INVISIBLE);
        list = inflated.findViewById(R.id.list);
        button = inflated.findViewById(R.id.button);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.temperatureData.observe(
            this,
            temperature -> {
                text1.setText(String.format("Input: %s", temperature));
                temperature.convert();
                text2.setText(String.format("Output: %s", temperature));
            }
        );

        check.setOnCheckedChangeListener(
            (v, isChecked) -> {
                int stubVisibility = isChecked ? View.VISIBLE : View.GONE;
                int nonStubVisibility = !isChecked ? View.VISIBLE : View.GONE;
                stub.setVisibility(stubVisibility);
                text1.setVisibility(nonStubVisibility);
                text2.setVisibility(nonStubVisibility);
                slider.setVisibility(nonStubVisibility);
                check.setVisibility(nonStubVisibility);
            }
        );
        slider.addOnChangeListener(
            (slider, value, fromUser) ->
                viewModel.temperatureData
                    .setValue(new Temperature((int) value, Temperature.Unit.CELSIUS))
        );

        list.setAdapter(new ForecastAdapter());
        button.setOnClickListener(view -> check.setChecked(false));
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

    @Override
    public void onBackPressed() {
        if (check.isChecked()) {
            check.setChecked(false);
            return;
        }
        super.onBackPressed();
    }
}
