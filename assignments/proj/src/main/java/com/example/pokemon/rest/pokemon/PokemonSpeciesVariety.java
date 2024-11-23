package com.example.pokemon.rest.pokemon;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class PokemonSpeciesVariety {
    @SerializedName("is_default")
    public boolean isDefault;

    public Pokemon pokemon;
}
