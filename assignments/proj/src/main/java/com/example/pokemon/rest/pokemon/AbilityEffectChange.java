package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.games.VersionGroup;
import com.example.pokemon.rest.utilities.Effect;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class AbilityEffectChange {
    @SerializedName("effect_entries")
    public List<Effect> effectEntries;

    @SerializedName("version_group")
    public VersionGroup versiongroup;
}
