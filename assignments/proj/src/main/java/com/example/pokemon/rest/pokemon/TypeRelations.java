package com.example.pokemon.rest.pokemon;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class TypeRelations {
    @SerializedName("no_damage_to")
    public List<Type> noDamageTo;

    @SerializedName("half_damage_to")
    public List<Type> halfDamageTo;

    @SerializedName("double_damage_to")
    public List<Type> doubleDamageTo;

    @SerializedName("no_damage_from")
    public List<Type> noDamageFrom;

    @SerializedName("half_damage_from")
    public List<Type> halfDamageFrom;

    @SerializedName("double_damage_from")
    public List<Type> doubleDamageFrom;
}
