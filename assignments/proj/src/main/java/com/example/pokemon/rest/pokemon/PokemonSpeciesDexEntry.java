package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.games.Pokedex;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class PokemonSpeciesDexEntry {
    @SerializedName("entry_number")
    public int entryNumber;

    public Pokedex pokedex;
}
