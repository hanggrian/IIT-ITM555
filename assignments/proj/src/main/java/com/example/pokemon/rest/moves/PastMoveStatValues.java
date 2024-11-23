package com.example.pokemon.rest.moves;

import com.example.pokemon.rest.games.VersionGroup;
import com.example.pokemon.rest.pokemon.Type;
import com.example.pokemon.rest.utilities.VerboseEffect;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class PastMoveStatValues {
    public int accuracy;

    @SerializedName("effect_chance")
    public int effectChance;

    public int power;
    public int pp;

    @SerializedName("effect_entries")
    public List<VerboseEffect> effectEntries;

    public Type type;

    @SerializedName("version_group")
    public VersionGroup versionGroup;
}
