package com.example.playlist;

import static com.google.common.truth.Truth.assertThat;

import com.example.playlist.data.Music;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
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
    public void recyclerAdapter() {
        try {
            List<Music> musics = activity.api.getMusicCatalog().execute().body().getBluesy();
            activity.adapter.replaceAll(musics);

            assertThat(activity.adapter.getItemViewType(0)).isEqualTo(MusicAdapter.TYPE_GROUP);
            assertThat(activity.adapter.getItemViewType(1)).isEqualTo(MusicAdapter.TYPE_INFO);
            assertThat(activity.adapter.getItemCount())
                .isEqualTo(
                    musics.size()
                        + musics
                        .stream()
                        .map((Function<Music, Object>) Music::getCountry)
                        .distinct()
                        .count()
                );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
