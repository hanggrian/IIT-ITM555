package com.example.quotes;

import static com.google.common.truth.Truth.assertThat;

import android.content.Intent;
import java.util.Objects;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.internal.DoNotInstrument;

@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class QuoteDetailActivityTest {
    private QuoteDetailActivity activity;

    @Before
    public void setup() {
        Intent intent = new Intent();
        intent.putExtra(QuoteDetailActivity.EXTRA_POSITION, 0);
        intent.putExtra(QuoteDetailActivity.EXTRA_DATA_SOURCE, new DataSource());
        activity = Robolectric.buildActivity(QuoteDetailActivity.class, intent).setup().get();
    }

    @Test
    public void intentReceiving() {
        assertThat(activity.image.getDrawable().getConstantState())
            .isEqualTo(
                Objects
                    .requireNonNull(activity.getDrawable(R.drawable.steve1))
                    .getConstantState()
            );
        assertThat(activity.text.getText()).isEqualTo(activity.getString(R.string.quote1));
    }
}
