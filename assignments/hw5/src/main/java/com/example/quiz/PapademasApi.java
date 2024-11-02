package com.example.quiz;

import androidx.annotation.NonNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * A REST API invocation site to retrieve pop quiz questions from the Papademas website.
 */
public interface PapademasApi {
    String ENDPOINT = "http://www.papademas.net:81";

    @GET("/sample.txt")
    Call<ResponseBody> getQuestions();

    @NonNull
    default List<String> getRandomizedQuestions() throws IOException {
        List<String> questions =
            Arrays.asList(
                getQuestions()
                    .execute()
                    .body()
                    .string()
                    .split("\n")
            );
        Collections.shuffle(questions);
        return questions;
    }

    /**
     * Convenient method to instantiate builder.
     */
    @NonNull
    static PapademasApi create() {
        return new Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .build()
            .create(PapademasApi.class);
    }
}
