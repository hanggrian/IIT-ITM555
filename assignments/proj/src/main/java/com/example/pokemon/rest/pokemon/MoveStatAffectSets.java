package com.example.pokemon.rest.pokemon;

import java.util.List;
import org.parceler.Parcel;

@Parcel
public class MoveStatAffectSets {
    public List<MoveStatAffect> increase;
    public List<MoveStatAffect> decrease;
}
