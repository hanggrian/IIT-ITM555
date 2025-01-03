package com.example.pokemon;

import com.example.pokemon.db.Members;
import com.example.pokemon.db.PokemonDatabase;
import com.example.pokemon.db.schema.Member;
import com.example.pokemon.ui.main.MainActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.internal.DoNotInstrument;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class MembersTest {
    private PokemonDatabase db;

    @Before
    public void setup() {
        db =
            PokemonDatabase.from(
                Robolectric.buildActivity(MainActivity.class).setup().get().getApplicationContext(),
                true
            );
    }

    @Test
    public void test() {
        Members members = db.members();
        members.deleteAll();

        // create
        Member member =
            new Member.Builder()
                .pokemon("pikachu")
                .move1("quick-attack")
                .build();
        members.insertAll(member);
        assertThat(members.getCount())
            .isEqualTo(1);

        // read
        assertThat(member.getPokemon())
            .isEqualTo("pikachu");
        assertThat(member.getMove1())
            .isEqualTo("quick-attack");

        // update
        member.setPokemon("rattata");
        member.setMove1("bite");
        members.update(member);
        assertThat(member.getPokemon())
            .isEqualTo("rattata");
        assertThat(member.getMove1())
            .isEqualTo("bite");

        // delete
        members.delete(member);
        assertThat(members.isExist("rattata"))
            .isFalse();
    }

    @After
    public void finish() {
        db.close();
    }
}
