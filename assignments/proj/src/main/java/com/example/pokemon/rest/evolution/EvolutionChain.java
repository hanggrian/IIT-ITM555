package com.example.pokemon.rest.evolution;

import com.example.pokemon.rest.utilities.ApiResource;
import org.parceler.Parcel;

@Parcel
public class EvolutionChain extends ApiResource {
    public int id;

    // public Item baby_trigger_item;

    public ChainLink chain;
}
