package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.utilities.Name;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class PokemonHabitat extends NamedApiResource {
    public int id;
    public List<Name> names;

    @SerializedName("pokemon_species")
    public List<PokemonSpecies> pokemonSpecies;
}
