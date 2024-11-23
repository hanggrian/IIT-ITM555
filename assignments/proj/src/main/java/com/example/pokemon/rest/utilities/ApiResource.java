package com.example.pokemon.rest.utilities;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class ApiResource {
    public String url;

    @SerializedName("is_fetched")
    public boolean isFetched;
}
