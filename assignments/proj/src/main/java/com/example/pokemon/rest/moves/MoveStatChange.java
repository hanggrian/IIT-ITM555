package com.example.pokemon.rest.moves;

import com.example.pokemon.rest.pokemon.Stat;
import org.parceler.Parcel;

@Parcel
public class MoveStatChange {
    public int change;
    public Stat stat;
}
