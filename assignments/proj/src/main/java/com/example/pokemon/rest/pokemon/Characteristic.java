package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.utilities.ApiResource;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class Characteristic extends ApiResource {
    public int id;

    @SerializedName("gene_modulo")
    public int geneModulo;

    @SerializedName("possible_values")
    public int possibleValues;
}
