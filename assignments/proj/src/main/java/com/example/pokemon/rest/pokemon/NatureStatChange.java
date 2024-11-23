package com.example.pokemon.rest.pokemon;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class NatureStatChange {
    @SerializedName("max_change")
    public int maxChange;

    @SerializedName("pokeathlon_stat")
    public PokeathlonStat pokeathlonStat;
}
