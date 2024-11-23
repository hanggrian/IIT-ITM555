package com.example.pokemon.rest.evolution;

import com.example.pokemon.rest.moves.Move;
import com.example.pokemon.rest.pokemon.PokemonSpecies;
import com.example.pokemon.rest.pokemon.Type;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class EvolutionDetail {
    // public Item item;

    public EvolutionTrigger trigger;
    public int gender;

    // public Item held_item;

    @SerializedName("known_move")
    public Move knownMove;

    @SerializedName("known_move_type")
    public Type knownMoveType;

    // public Location location;

    @SerializedName("min_level")
    public int minLevel;

    @SerializedName("min_happiness")
    public int minHappiness;

    @SerializedName("min_beauty")
    public int minBeauty;

    @SerializedName("min_affection")
    public int minAffection;

    @SerializedName("needs_overworld_rain")
    public boolean needsOverworldRain;

    @SerializedName("party_species")
    public PokemonSpecies partySpecies;

    @SerializedName("party_type")
    public Type partyType;

    @SerializedName("relative_physical_stats")
    public int relativePhysicalStats;

    @SerializedName("time_of_day")
    public String timeOfDay;

    @SerializedName("trade_species")
    public PokemonSpecies tradeSpecies;

    @SerializedName("turn_upside_down")
    public boolean turnUpsideDown;
}
