package com.example.pokemon.rest.moves;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class ContestComboDetail {
    @SerializedName("use_before")
    public List<Move> useBefore;

    @SerializedName("use_after")
    public List<Move> useAfter;
}
