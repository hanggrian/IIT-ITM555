package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.utilities.Language;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class AwesomeName {
    @SerializedName("awesome_name")
    public String awesomeName;

    public Language language;
}
