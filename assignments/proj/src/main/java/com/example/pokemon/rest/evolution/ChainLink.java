package com.example.pokemon.rest.evolution;

import com.example.pokemon.rest.pokemon.PokemonSpecies;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class ChainLink {
    @SerializedName("is_baby")
    public boolean isBaby;

    public PokemonSpecies species;

    @SerializedName("evolution_details")
    public List<EvolutionDetail> evolutionDetails;

    @SerializedName("evolves_to")
    public List<ChainLink> evolvesTo;
}
