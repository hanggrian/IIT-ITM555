package com.example.pokemon.rest.utilities;

import com.example.pokemon.rest.games.Version;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class VersionGameIndex {
    @SerializedName("game_index")
    public int gameIndex;

    public Version version;
}
