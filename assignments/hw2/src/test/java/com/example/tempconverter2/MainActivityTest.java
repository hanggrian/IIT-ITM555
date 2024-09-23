package com.example.tempconverter2;

import static com.google.common.truth.Truth.assertThat;

import android.view.View;
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
    public void testSlider() {
        activity.slider.setValue(10);

        assertThat(activity.text1.getText().toString()).isEqualTo("Input: 10.0 °C");
        assertThat(activity.text2.getText().toString()).isEqualTo("Output: 50.0 °F");
    }

    @Test
    public void testCheck() {
        activity.check.setChecked(true);

        assertThat(activity.text1.getVisibility()).isEqualTo(View.GONE);
        assertThat(activity.text1.getVisibility()).isEqualTo(View.GONE);
        assertThat(activity.check.getVisibility()).isEqualTo(View.GONE);
        assertThat(activity.slider.getVisibility()).isEqualTo(View.GONE);
    }
}
