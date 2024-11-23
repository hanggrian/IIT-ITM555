package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class Gender extends NamedApiResource {
    public int id;

    @SerializedName("pokemon_species_details")
    public List<PokemonSpeciesGender> pokemonSpeciesDetails;

    @SerializedName("required_for_evolution")
    public List<PokemonSpecies> requiredForEvolution;
}
