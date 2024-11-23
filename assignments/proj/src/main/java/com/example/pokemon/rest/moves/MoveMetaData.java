package com.example.pokemon.rest.moves;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class MoveMetaData {
    public MoveAilment ailment;
    public MoveCategory category;

    @SerializedName("min_hits")
    public int minHits;

    @SerializedName("max_hits")
    public int maxHits;

    @SerializedName("min_turns")
    public int minTurns;

    @SerializedName("max_turns")
    public int maxTurns;

    public int drain;
    public int healing;

    @SerializedName("crit_rate")
    public int critRate;

    @SerializedName("ailment_chance")
    public int ailmentChance;

    @SerializedName("flinch_chance")
    public int flinchChance;

    @SerializedName("stat_chance")
    public int statChance;
}
