package com.example.pokemon.rest.utilities;

import com.example.pokemon.rest.games.Version;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class VersionEncounterDetail {
    public Version version;

    @SerializedName("max_chance")
    public int maxChance;

    @SerializedName("encounter_details")
    public List<Encounter> encounterDetails;
}
