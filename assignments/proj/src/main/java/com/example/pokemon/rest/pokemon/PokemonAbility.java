package com.example.pokemon.rest.pokemon;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class PokemonAbility {
    @SerializedName("is_hidden")
    public boolean isHidden;

    public int slot;

    public Ability ability;
}
