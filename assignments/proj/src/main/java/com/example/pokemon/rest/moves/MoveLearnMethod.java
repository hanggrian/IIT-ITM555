package com.example.pokemon.rest.moves;

import com.example.pokemon.rest.games.VersionGroup;
import com.example.pokemon.rest.utilities.Description;
import com.example.pokemon.rest.utilities.Name;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class MoveLearnMethod extends NamedApiResource {
    public int id;
    public List<Description> descriptions;
    public List<Name> names;

    @SerializedName("version_groups")
    public List<VersionGroup> versionGroups;
}
