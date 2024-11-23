package com.example.pokemon.rest.pokemon;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class NaturePokeathlonStatAffect {
    @SerializedName("max_change")
    public int maxChange;

    public Nature nature;
}
