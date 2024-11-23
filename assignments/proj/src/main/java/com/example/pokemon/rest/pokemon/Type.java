package com.example.pokemon.rest.pokemon;

import com.example.pokemon.rest.games.Generation;
import com.example.pokemon.rest.moves.Move;
import com.example.pokemon.rest.moves.MoveDamageClass;
import com.example.pokemon.rest.utilities.GenerationGameIndex;
import com.example.pokemon.rest.utilities.Name;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class Type extends NamedApiResource {
    public Sprites sprites;
    public int id;

    @SerializedName("damage_relations")
    public TypeRelations damageRelations;

    @SerializedName("game_indices")
    public List<GenerationGameIndex> gameIndices;

    public Generation generation;

    @SerializedName("move_damage_class")
    public MoveDamageClass moveDamageClass;

    public List<Name> names;
    public List<TypePokemon> pokemon;
    public List<Move> moves;

    @Parcel
    public static class Icon {
        @SerializedName("name_icon")
        public String nameIcon;
    }

    @Parcel
    public static class Sprites {
        @SerializedName("generation-iii")
        public Generation3 generation3;

        @SerializedName("generation-iv")
        public Generation4 generation4;

        @SerializedName("generation-v")
        public Generation5 generation5;

        @SerializedName("generation-vi")
        public Generation6 generation6;

        @SerializedName("generation-vii")
        public Generation7 generation7;

        @SerializedName("generation-viii")
        public Generation8 generation8;

        @SerializedName("generation-ix")
        public Generation9 generation9;

        @Parcel
        public static class Generation3 {
            public Icon colosseum;
            public Icon emerald;

            @SerializedName("firered-leafgreen")
            public Icon fireredLeafgreen;

            @SerializedName("ruby-sapphire")
            public Icon rubySapphire;

            public Icon xd;
        }

        @Parcel
        public static class Generation4 {
            @SerializedName("diamond-pearl")
            public Icon diamondPearl;

            @SerializedName("heartgold-soulsilver")
            public Icon heartgoldSoulsilver;

            public Icon platinum;
        }

        @Parcel
        public static class Generation5 {
            @SerializedName("black-2-white-2")
            public Icon black2White2;

            @SerializedName("black-white")
            public Icon blackWhite;
        }

        @Parcel
        public static class Generation6 {
            @SerializedName("omega-ruby-alpha-sapphire")
            public Icon omegaRubyAlphaSapphire;

            @SerializedName("x-y")
            public Icon xY;
        }

        @Parcel
        public static class Generation7 {
            @SerializedName("lets-go-pikachu-lets-go-eevee")
            public Icon letsGoPikachuLetsGoEevee;

            @SerializedName("sun-moon")
            public Icon sunMoon;

            @SerializedName("ultra-sun-ultra-moon")
            public Icon ultraSunUltraMoon;
        }

        @Parcel
        public static class Generation8 {
            @SerializedName("brilliant-diamond-and-shining-pearl")
            public Icon brilliantDiamondAndShiningPearl;

            @SerializedName("legends-arceus")
            public Icon legendsArceus;

            @SerializedName("sword-shield")
            public Icon swordShield;
        }

        @Parcel
        public static class Generation9 {
            @SerializedName("scarlet-violet")
            public Icon scarletViolet;
        }
    }
}
