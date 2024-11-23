package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.utilities.Description;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class GrowthRate extends NamedApiResource {
    public int id;
    public String formula;
    public List<Description> descriptions;
    public List<GrowthRateExperienceLevel> levels;

    @SerializedName("pokemon_species")
    public List<PokemonSpecies> pokemonSpecies;
}
