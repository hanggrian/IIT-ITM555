package com.example.pokemon.rest.pokemon;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class PokemonFormSprites {
    @SerializedName("front_default")
    public String frontDefault;

    @SerializedName("front_shiny")
    public String frontShiny;

    @SerializedName("back_default")
    public String backDefault;

    @SerializedName("back_shiny")
    public String backShiny;
}
