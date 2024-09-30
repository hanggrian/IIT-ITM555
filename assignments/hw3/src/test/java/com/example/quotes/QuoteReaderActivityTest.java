package com.example.quotes;

import static com.google.common.truth.Truth.assertThat;

import android.widget.ListAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.internal.DoNotInstrument;

@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class QuoteReaderActivityTest {
    private QuoteReaderActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(QuoteReaderActivity.class).setup().get();
    }

    @Test
    public void quoteContent() {
        ListAdapter adapter = activity.list.getAdapter();

        assertThat(adapter.getItem(0)).isEqualTo(R.string.quote1);
        assertThat(adapter.getItemId(0)).isEqualTo(0);

        int lastIndex = adapter.getCount() - 1;
        assertThat(adapter.getItem(lastIndex)).isEqualTo(R.string.quote10);
        assertThat(adapter.getItemId(lastIndex)).isEqualTo(lastIndex);
    }
}
