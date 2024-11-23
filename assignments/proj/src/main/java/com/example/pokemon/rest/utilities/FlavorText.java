package com.example.pokemon.rest.utilities;

import com.example.pokemon.rest.games.Version;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class FlavorText {
    @SerializedName("flavor_text")
    public String flavorText;

    public Language language;
    public Version version;
}
