package com.example.playlist;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.internal.DoNotInstrument;

@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class LoginActivityTest {
    private LoginActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(LoginActivity.class).setup().get();
    }

    @Test
    public void buttonState() {
        assertThat(activity.proceedButton.isEnabled()).isFalse();

        activity.usernameEdit.setText("1234");
        activity.passwordEdit.setText(" ");
        assertThat(activity.proceedButton.isEnabled()).isTrue();
    }
}
