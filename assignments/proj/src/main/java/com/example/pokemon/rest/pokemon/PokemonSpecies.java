package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.games.Generation;
import com.example.pokemon.rest.utilities.Description;
import com.example.pokemon.rest.utilities.FlavorText;
import com.example.pokemon.rest.utilities.Name;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class PokemonSpecies extends NamedApiResource {
    public int id;
    public int order;

    @SerializedName("gender_rate")
    public int genderRate;

    @SerializedName("capture_rate")
    public int captureRate;

    @SerializedName("base_happiness")
    public int baseHappiness;

    @SerializedName("is_baby")
    public boolean isBaby;

    @SerializedName("is_legendary")
    public boolean isLegendary;

    @SerializedName("is_mythical")
    public boolean isMythical;

    @SerializedName("hatch_counter")
    public int hatchCounter;

    @SerializedName("has_gender_differences")
    public boolean hasGenderDifferences;

    @SerializedName("forms_switchable")
    public boolean formsSwitchable;

    @SerializedName("growth_rate")
    public GrowthRate growthRate;

    @SerializedName("pokedex_numbers")
    public List<PokemonSpeciesDexEntry> pokedexNumbers;

    @SerializedName("egg_groups")
    public List<EggGroup> eggGroups;

    public PokemonColor color;
    public PokemonShape shape;

    @SerializedName("evolves_from_species")
    public PokemonSpecies evolvesFromSpecies;

    @SerializedName("evolution_chain")
    public NamedApiResource evolutionChain;

    public PokemonHabitat habitat;
    public Generation generation;
    public List<Name> names;

    @SerializedName("pal_park_encounters")
    public List<PalParkEncounterArea> palParkEncounters;

    @SerializedName("flavor_text_entries")
    public List<FlavorText> flavorTextEntries;

    @SerializedName("form_descriptions")
    public List<Description> formDescriptions;

    public List<Genus> genus;
    public List<PokemonSpeciesVariety> varieties;
}
