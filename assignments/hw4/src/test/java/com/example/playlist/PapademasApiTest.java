package com.example.playlist;

import static com.google.common.truth.Truth.assertThat;

import com.example.playlist.data.Music;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class PapademasApiTest {
    private PapademasApi api;

    @Before
    public void init() {
        api = PapademasApi.create();
    }

    @Test
    public void getBluesy() {
        try {
            List<Music> musics = api.getMusicCatalog().execute().body().getBluesy();
            assertThat(musics).isNotEmpty();

            Music music = musics.get(0);
            assertThat(music.isSold()).isTrue();
            assertThat(music.getTitle()).isEqualTo("Empires Burlesque");
            assertThat(music.getArtist()).isEqualTo("Bob Dylan");
            assertThat(music.getCountry()).isEqualTo("USA");
            assertThat(music.getCompany()).isEqualTo("Columbia");
            assertThat(music.getPrice()).isEqualTo(10.9);
            assertThat(music.getYear()).isEqualTo("1985");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
