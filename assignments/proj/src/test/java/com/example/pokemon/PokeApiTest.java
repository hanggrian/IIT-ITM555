package com.example.pokemon;

import com.example.pokemon.rest.PokeApi;
import com.example.pokemon.rest.moves.Move;
import com.example.pokemon.rest.pokemon.Pokemon;
import com.example.pokemon.rest.resources.NamedApiResourceList;
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
    public void listPokemons() {
        NamedApiResourceList list;
        try {
            list = api.listPokemons(10, 20).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertThat(list)
            .isNotNull();
        assertThat(list.results.size())
            .isEqualTo(10);
        assertThat(list.getCurrentPage())
            .isEqualTo(2);
    }

    @Test
    public void listMoves() {
        NamedApiResourceList list;
        try {
            list = api.listMoves(20, 40).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertThat(list)
            .isNotNull();
        assertThat(list.results.size())
            .isEqualTo(20);
        assertThat(list.getCurrentPage())
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
        assertThat(pokemon.name)
            .isEqualTo("pidgey");
        assertThat(pokemon.types.get(0).type.name)
            .isEqualTo("normal");
    }

    @Test
    public void getMove() {
        Move move;
        try {
            move = api.getMove("ice-punch").get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertThat(move)
            .isNotNull();
        assertThat(move.name)
            .isEqualTo("ice-punch");
        assertThat(move.type.name)
            .isEqualTo("ice");
    }
}
