package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.moves.MoveDamageClass;
import com.example.pokemon.rest.utilities.Name;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class Stat extends NamedApiResource {
    public int id;

    @SerializedName("game_index")
    public int gameIndex;

    @SerializedName("is_battle_only")
    public boolean isBattleOnly;

    @SerializedName("affecting_moves")
    public MoveStatAffectSets affectingMoves;

    @SerializedName("affecting_natures")
    public NatureStatAffectSets affectingNatures;

    public List<Characteristic> characteristics;

    @SerializedName("move_damage_class")
    public MoveDamageClass moveDamageClass;

    public List<Name> names;
}
