package com.example.pokemon.rest.moves;

import com.example.pokemon.rest.games.VersionGroup;
import com.example.pokemon.rest.utilities.Language;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class MoveFlavorText {
    @SerializedName("flavor_text")
    public String flavorText;

    public Language language;

    @SerializedName("version_group")
    public VersionGroup versionGroup;
}
