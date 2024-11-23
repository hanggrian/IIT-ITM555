package com.example.pokemon.rest.utilities;

import com.example.pokemon.rest.games.Generation;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class GenerationGameIndex {
    @SerializedName("game_index")
    public int gameIndex;

    public Generation generation;
}
