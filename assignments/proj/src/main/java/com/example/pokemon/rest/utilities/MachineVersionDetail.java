package com.example.pokemon.rest.utilities;

import com.example.pokemon.rest.games.VersionGroup;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class MachineVersionDetail {
    // public Machine machine;

    @SerializedName("version_group")
    public VersionGroup versionGroup;
}
