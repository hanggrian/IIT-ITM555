package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.moves.MoveBattleStyle;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class MoveBattleStylePreference {
    @SerializedName("low_hp_preference")
    public int lowHpPreference;

    @SerializedName("high_hp_preference")
    public int highHpPreference;

    @SerializedName("move_battle_style")
    public MoveBattleStyle moveBattleStyle;
}
