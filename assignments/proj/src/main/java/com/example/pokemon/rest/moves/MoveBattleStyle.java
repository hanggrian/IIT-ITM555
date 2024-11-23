package com.example.pokemon.rest.moves;

import com.example.pokemon.rest.utilities.Name;
import com.example.pokemon.rest.utilities.NamedApiResource;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class MoveBattleStyle extends NamedApiResource {
    public int id;
    public List<Name> names;
}