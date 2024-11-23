package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.games.Generation;
import com.example.pokemon.rest.utilities.Name;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.example.pokemon.rest.utilities.VerboseEffect;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class Ability extends NamedApiResource {
    public int id;

    @SerializedName("is_main_series")
    public boolean isMainSeries;

    public Generation generation;
    public List<Name> names;

    @SerializedName("effect_entries")
    public List<VerboseEffect> effectEntries;

    @SerializedName("effect_changes")
    public List<AbilityEffectChange> effectChanges;

    @SerializedName("flavor_text_entries")
    public List<AbilityFlavorText> flavorTextEntries;

    public List<AbilityPokemon> pokemon;
}
