package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.games.VersionGroup;
import com.example.pokemon.rest.moves.MoveLearnMethod;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class PokemonMoveVersion {
    @SerializedName("move_learn_method")
    public MoveLearnMethod moveLearnMethod;

    @SerializedName("version_group")
    public VersionGroup versionGroup;

    @SerializedName("level_learned_at")
    public int levelLearnedAt;
}
