package com.example.pokemon.rest.pokemon;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class PokemonSprites {
    @SerializedName("front_default")
    public String frontDefault;

    @SerializedName("front_shiny")
    public String frontShiny;

    @SerializedName("front_female")
    public String frontFemale;

    @SerializedName("front_shiny_female")
    public String frontShinyFemale;

    @SerializedName("back_default")
    public String backDefault;

    @SerializedName("back_shiny")
    public String backShiny;

    @SerializedName("back_female")
    public String backFemale;

    @SerializedName("back_shiny_female")
    public String backShinyFemale;
}
