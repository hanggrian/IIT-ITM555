package com.example.marks;

import android.view.View;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.internal.DoNotInstrument;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class MainActivityTest {
    private MainActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MainActivity.class).setup().get();
    }

    @Test
    public void buttonVisibility() {
        assertThat(activity.action.getVisibility())
            .isEqualTo(View.GONE);

        activity.idEdit.setText("0");
        activity.nameEdit.setText("John Doe");
        activity.valueEdit.setText("A");

        assertThat(activity.action.getVisibility())
            .isEqualTo(View.VISIBLE);
    }
}
