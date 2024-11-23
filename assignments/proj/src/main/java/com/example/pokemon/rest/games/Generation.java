package com.example.pokemon.rest.games;

import android.graphics.Region;
import com.example.pokemon.rest.moves.Move;
import com.example.pokemon.rest.pokemon.Ability;
import com.example.pokemon.rest.pokemon.PokemonSpecies;
import com.example.pokemon.rest.pokemon.Type;
import com.example.pokemon.rest.utilities.Name;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class Generation extends NamedApiResource {
    public int id;
    public List<Ability> abilities;

    public List<Name> names;

    @SerializedName("main_region")
    public Region mainRegion;

    public List<Move> moves;

    @SerializedName("pokemon_species")
    public List<PokemonSpecies> pokemonSpecies;

    public List<Type> types;

    @SerializedName("version_groups")
    public List<VersionGroup> versionGroups;
}
