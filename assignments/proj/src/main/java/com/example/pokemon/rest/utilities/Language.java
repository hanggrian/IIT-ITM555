package com.example.pokemon.rest.utilities;

import java.util.List;
import org.parceler.Parcel;

@Parcel
public class Language extends NamedApiResource {
    public int id;
    public boolean official;
    public String iso639;
    public String iso3166;
    public List<Name> names;
}
