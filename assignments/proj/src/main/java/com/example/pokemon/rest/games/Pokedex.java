package com.example.pokemon.rest.games;

import com.example.pokemon.rest.utilities.Description;
import com.example.pokemon.rest.utilities.Name;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class Pokedex extends NamedApiResource {
    public int id;

    @SerializedName("is_main_series")
    public boolean isMainSeries;

    public List<Description> descriptions;
    public List<Name> names;

    @SerializedName("pokemon_entries")
    public List<PokemonEntry> pokemonEntries;

    // public Region region;

    @SerializedName("version_groups")
    public List<VersionGroup> versionGroups;
}
