package com.example.tempconverter1;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.internal.DoNotInstrument;

@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class MainActivityTest {
    private MainActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MainActivity.class).setup().get();
    }

    @Test
    public void fahrenheitToCelsius() {
        activity.input.setText("10");

        assertThat(activity.titleText.getText().toString()).isEqualTo("-12.2 °C");
    }

    @Test
    public void celsiusToFahrenheit() {
        activity.celsiusRadio.setChecked(true);
        activity.input.setText("0");

        assertThat(activity.titleText.getText().toString()).isEqualTo("32.0 °F");
    }
}
