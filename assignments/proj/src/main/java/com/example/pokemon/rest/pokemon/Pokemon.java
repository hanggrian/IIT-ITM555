package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.utilities.NamedApiResource;
import com.example.pokemon.rest.utilities.VersionGameIndex;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class Pokemon extends NamedApiResource {
    public int id;

    @SerializedName("base_experience")
    public int baseExperience;

    public int height;

    @SerializedName("is_default")
    public boolean isDefault;

    public int order;
    public int weight;
    // public List<PokemonAbility> abilities;
    // public List<PokemonForm> forms;

    @SerializedName("game_indices")
    public List<VersionGameIndex> gameIndices;

    @SerializedName("held_items")
    public List<PokemonHeldItem> heldItems;

    @SerializedName("location_area_encounters")
    public String locationAreaEncounters;

    public List<PokemonMove> moves;
    public PokemonSprites sprites;
    public NamedApiResource species;
    public List<PokemonStat> stats;
    public List<PokemonType> types;
}
