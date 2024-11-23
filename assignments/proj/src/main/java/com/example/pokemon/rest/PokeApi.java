package com.example.pokemon.rest;

import androidx.annotation.NonNull;
import com.example.pokemon.rest.evolution.EvolutionChain;
import com.example.pokemon.rest.moves.Move;
import com.example.pokemon.rest.pokemon.Pokemon;
import com.example.pokemon.rest.pokemon.PokemonSpecies;
import com.example.pokemon.rest.pokemon.Type;
import com.example.pokemon.rest.resources.NamedApiResourceList;
import com.google.common.util.concurrent.ListenableFuture;
import retrofit2.Retrofit;
import retrofit2.adapter.guava.GuavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApi {
    String ENDPOINT = "https://pokeapi.co/api/v2/";

    @GET("pokemon")
    ListenableFuture<NamedApiResourceList> listPokemons(
        @Query("limit") int limit,
        @Query("offset") int offset
    );

    @GET("move")
    ListenableFuture<NamedApiResourceList> listMoves(
        @Query("limit") int limit,
        @Query("offset") int offset
    );

    @GET("pokemon/{name}")
    ListenableFuture<Pokemon> getPokemon(@Path("name") String name);

    @GET("move/{name}")
    ListenableFuture<Move> getMove(@Path("name") String name);

    @GET("type/{name}")
    ListenableFuture<Type> getType(@Path("name") String name);

    @GET("pokemon-species/{id}")
    ListenableFuture<PokemonSpecies> getPokemonSpecies(@Path("id") int id);

    @GET("evolution-chain/{id}")
    ListenableFuture<EvolutionChain> getEvolutionChain(@Path("id") int id);

    @NonNull
    static PokeApi create() {
        return new Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(GuavaCallAdapterFactory.create())
            .build()
            .create(PokeApi.class);
    }
}
