package com.example.pokemon.rest.pokemon;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class PokemonSpeciesGender {
    public int rate;

    @SerializedName("pokemon_species")
    public PokemonSpecies pokemonSpecies;
}
