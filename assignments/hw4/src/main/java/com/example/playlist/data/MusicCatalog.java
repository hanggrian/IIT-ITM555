package com.example.playlist.data;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MusicCatalog {
    @SerializedName("Bluesy_Music")
    private List<Music> bluesy;

    public List<Music> getBluesy() {
        return bluesy;
    }

    public void setBluesy(List<Music> bluesy) {
        this.bluesy = bluesy;
    }
}
