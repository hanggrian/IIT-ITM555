package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class PokemonMove {
    public NamedApiResource move;

    @SerializedName("version_group_details")
    public List<PokemonMoveVersion> versionGroupDetails;
}
