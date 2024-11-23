package com.example.pokemon.rest.pokemon;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class PokemonStat {
    public Stat stat;
    public int effort;

    @SerializedName("base_stat")
    public int baseStat;
}
