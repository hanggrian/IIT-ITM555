package com.example.quiz;

import static com.google.common.truth.Truth.assertThat;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class PapademasApiTest {
    private PapademasApi api;

    @Before
    public void init() {
        api = PapademasApi.create();
    }

    @Test
    public void getQuestion() {
        try {
            assertThat(api.getQuestions().execute().body().string())
                .isEqualTo(
                    "Android's current stable OS release is Android 14.\n"
                        + "An AsyncTask is tied to the life cycle of the Activity that contains"
                        + " it.\n"
                        + "The last callback in the lifecycle of an activity is onDestroy()\n"
                        + "To collapse / expand items use the Code -> Folding menu in AS.\n"
                        + "You cannot start an Activity with an Intent."
                );

            assertThat(api.getRandomizedQuestions().size())
                .isEqualTo(5);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
