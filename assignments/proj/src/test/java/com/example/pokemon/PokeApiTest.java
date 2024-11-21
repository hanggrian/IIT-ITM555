package com.example.pokemon;

import com.example.pokemon.rest.PokeApi;
import com.example.pokemon.rest.schema.Pagination;
import com.example.pokemon.rest.schema.Pokemon;
import java.util.concurrent.ExecutionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.internal.DoNotInstrument;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class PokeApiTest {
    private PokeApi api;

    @Before
    public void setup() {
        api = PokeApi.create();
    }

    @Test
    public void listPokemon() {
        Pagination pagination;
        try {
            pagination = api.listPokemon(10, 20).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertThat(pagination)
            .isNotNull();
        assertThat(pagination.getResults().size())
            .isEqualTo(10);
        assertThat(pagination.getCurrentPage())
            .isEqualTo(2);
    }

    @Test
    public void getPokemon() {
        Pokemon pokemon;
        try {
            pokemon = api.getPokemon("pidgey").get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertThat(pokemon)
            .isNotNull();
        assertThat(pokemon.getName())
            .isEqualTo("pidgey");
        assertThat(pokemon.getTypes().get(0).getType().getName())
            .isEqualTo("normal");
    }
}
