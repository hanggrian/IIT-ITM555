package com.example.pokemon.rest.games;

import com.example.pokemon.rest.utilities.Name;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class Version extends NamedApiResource {
    public int id;
    public List<Name> names;

    @SerializedName("version_group")
    public VersionGroup versionGroup;
}
