package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.games.VersionGroup;
import com.example.pokemon.rest.utilities.Name;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class PokemonForm extends NamedApiResource {
    public int id;
    public int order;

    @SerializedName("form_order")
    public int formOrder;

    @SerializedName("is_default")
    public boolean isDefault;

    @SerializedName("is_battle_only")
    public boolean isBattleOnly;

    @SerializedName("is_mega")
    public boolean isMega;

    @SerializedName("form_name")
    public String formName;

    public Pokemon pokemon;
    public PokemonFormSprites sprites;

    @SerializedName("version_group")
    public VersionGroup versionGroup;

    public List<Name> names;

    @SerializedName("form_names")
    public List<Name> formNames;
}
