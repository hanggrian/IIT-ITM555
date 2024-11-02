package com.example.playlist;

import androidx.annotation.NonNull;
import com.example.playlist.data.MusicCatalog;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * A REST API invocation site to retrieve music catalog from the Papademas website.
 */
public interface PapademasApi {
    String ENDPOINT = "http://www.papademas.net:81";

    @NonNull
    @GET("/cd_catalog.json")
    Call<MusicCatalog> getMusicCatalog();

    /**
     * Convenient method to instantiate builder with GSON converter.
     */
    @NonNull
    static PapademasApi create() {
        return new Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PapademasApi.class);
    }
}
