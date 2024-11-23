package com.example.pokemon.rest.moves;

import com.example.pokemon.rest.utilities.Description;
import com.example.pokemon.rest.utilities.Name;
import com.example.pokemon.rest.utilities.NamedApiResource;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class MoveDamageClass extends NamedApiResource {
    public int id;
    public List<Description> descriptions;
    public List<Move> moves;
    public List<Name> names;
}
