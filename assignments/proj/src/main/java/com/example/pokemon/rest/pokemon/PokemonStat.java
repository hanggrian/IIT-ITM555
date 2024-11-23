package com.example.pokemon.rest.pokemon;

import com.example.pokemon.Numbers;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class PokemonStat {
    public static final String HP = "hp";
    public static final String ATK = "attack";
    public static final String DEF = "defense";
    public static final String SPC_ATK = "special-attack";
    public static final String SPC_DEF = "special-defense";
    public static final String SPD = "speed";

    // max base stats of generation 1
    public static final int MAX_HP = 250; // Chansey
    public static final int MAX_ATK = 134; // Dragonite
    public static final int MAX_DEF = 180; // Cloyster
    public static final int MAX_SPC_ATK = 154; // Mewtwo
    public static final int MAX_SPC_DEF = 154; // Mewtwo
    public static final int MAX_SPD = 140; // Electrode

    public NamedApiResource stat;
    public int effort;

    @SerializedName("base_stat")
    public int baseStat;

    public int getPercentage(int max) {
        return Numbers.getPercentage(baseStat, max);
    }
}
