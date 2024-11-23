package com.example.pokemon.rest.pokemon;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class PokemonHeldItem {
    // public Item item;

    @SerializedName("version_details")
    public List<PokemonHeldItemVersion> versionDetails;
}
