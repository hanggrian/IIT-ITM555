package com.example.pokemon.rest.utilities;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class Encounter {
    @SerializedName("min_level")
    public int minLevel;

    @SerializedName("max_level")
    public int maxLevel;

    // public List<EncounterConditionValue> condition_values;

    public int chance;

    // public EncounterMethod method;
}
