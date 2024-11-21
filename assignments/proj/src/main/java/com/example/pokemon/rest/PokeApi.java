package com.example.pokemon.rest;

import androidx.annotation.NonNull;
import com.example.pokemon.rest.schema.Pagination;
import com.example.pokemon.rest.schema.Pokemon;
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
    ListenableFuture<Pagination> listPokemon(
        @Query("limit") int limit,
        @Query("offset") int offset
    );

    @GET("pokemon/{name}")
    ListenableFuture<Pokemon> getPokemon(@Path("name") String name);

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
