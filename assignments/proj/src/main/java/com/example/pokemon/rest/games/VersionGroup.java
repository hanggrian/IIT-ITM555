package com.example.pokemon.rest.games;

import com.example.pokemon.rest.moves.MoveLearnMethod;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class VersionGroup extends NamedApiResource {
    public int id;
    public int order;
    public Generation generation;

    @SerializedName("move_learn_methods")
    public List<MoveLearnMethod> moveLearnMethods;

    public List<Pokedex> pokedexes;

    // public List<Region> regions;

    public List<Version> versions;
}
