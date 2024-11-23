package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.utilities.VersionEncounterDetail;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class LocationAreaEncounter {
    // public LocationArea location_area;

    @SerializedName("version_details")
    public List<VersionEncounterDetail> versionDetails;
}
