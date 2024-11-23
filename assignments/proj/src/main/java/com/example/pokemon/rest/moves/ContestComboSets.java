package com.example.pokemon.rest.moves;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class ContestComboSets {
    public ContestComboDetail normal;

    @SerializedName("super")
    public ContestComboDetail super2;
}
