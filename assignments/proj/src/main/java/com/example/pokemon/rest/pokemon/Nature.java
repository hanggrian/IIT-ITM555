package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.utilities.Name;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class Nature extends NamedApiResource {
    public int id;

    @SerializedName("decreased_stat")
    public Stat decreasedStat;

    @SerializedName("increased_stat")
    public Stat increasedStat;

    // public BerryFlavor hates_flavor;
    // public BerryFlavor likes_flavor;

    @SerializedName("pokeathlon_stat_changes")
    public List<NatureStatChange> pokeathlonStatChanges;

    @SerializedName("move_battle_style_preferences")
    public List<MoveBattleStylePreference> moveBattleStylePreferences;

    public List<Name> names;
}
