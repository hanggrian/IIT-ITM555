package com.example.pokemon.rest.utilities;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class VerboseEffect {
    public String effect;

    @SerializedName("short_effect")
    public String shortEffect;

    public Language language;
}
