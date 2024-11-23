package com.example.pokemon.rest.pokemon;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class PalParkEncounterArea {
    @SerializedName("base_score")
    public int baseScore;

    public int rate;
    // public PalParkArea area;
}
