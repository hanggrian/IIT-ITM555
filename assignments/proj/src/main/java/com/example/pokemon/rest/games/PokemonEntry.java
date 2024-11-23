package com.example.pokemon.rest.games;

import com.example.pokemon.rest.pokemon.PokemonSpecies;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class PokemonEntry {
    @SerializedName("entry_number")
    public int entryNumber;

    @SerializedName("pokemon_species")
    public PokemonSpecies pokemonSpecies;
}
