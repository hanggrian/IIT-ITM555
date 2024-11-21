package com.example.pokemon.rest.schema;

import com.example.pokemon.rest.schema.image.BackDefaultImaged;
import com.example.pokemon.rest.schema.image.BackFemaleImaged;
import com.example.pokemon.rest.schema.image.BackShinyFemaleImaged;
import com.example.pokemon.rest.schema.image.BackShinyImaged;
import com.example.pokemon.rest.schema.image.FrontDefaultImaged;
import com.example.pokemon.rest.schema.image.FrontFemaleImaged;
import com.example.pokemon.rest.schema.image.FrontShinyFemaleImaged;
import com.example.pokemon.rest.schema.image.FrontShinyImaged;
import com.example.pokemon.rest.schema.image.FullImage;
import com.google.gson.annotations.SerializedName;

public class Sprites implements
    BackDefaultImaged,
    BackFemaleImaged,
    BackShinyImaged,
    BackShinyFemaleImaged,
    FrontDefaultImaged,
    FrontFemaleImaged,
    FrontShinyImaged,
    FrontShinyFemaleImaged {
    @SerializedName("back_default")
    private String backDefault;

    @SerializedName("back_female")
    private String backFemale;

    @SerializedName("back_shiny")
    private String backShiny;

    @SerializedName("back_shiny_female")
    private String backShinyFemale;

    @SerializedName("front_default")
    private String frontDefault;

    @SerializedName("front_female")
    private String frontFemale;

    @SerializedName("front_shiny")
    private String frontShiny;

    @SerializedName("front_shiny_female")
    private String frontShinyFemale;

    private Other other;

    @Override
    public String getBackDefault() {
        return backDefault;
    }

    @Override
    public void setBackDefault(String backDefault) {
        this.backDefault = backDefault;
    }

    @Override
    public String getBackFemale() {
        return backFemale;
    }

    @Override
    public void setBackFemale(String backFemale) {
        this.backFemale = backFemale;
    }

    @Override
    public String getBackShiny() {
        return backShiny;
    }

    @Override
    public void setBackShiny(String backShiny) {
        this.backShiny = backShiny;
    }

    @Override
    public String getBackShinyFemale() {
        return backShinyFemale;
    }

    @Override
    public void setBackShinyFemale(String backShinyFemale) {
        this.backShinyFemale = backShinyFemale;
    }

    @Override
    public String getFrontDefault() {
        return frontDefault;
    }

    @Override
    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    @Override
    public String getFrontFemale() {
        return frontFemale;
    }

    @Override
    public void setFrontFemale(String frontFemale) {
        this.frontFemale = frontFemale;
    }

    @Override
    public String getFrontShiny() {
        return frontShiny;
    }

    @Override
    public void setFrontShiny(String frontShiny) {
        this.frontShiny = frontShiny;
    }

    @Override
    public String getFrontShinyFemale() {
        return frontShinyFemale;
    }

    @Override
    public void setFrontShinyFemale(String frontShinyFemale) {
        this.frontShinyFemale = frontShinyFemale;
    }

    public Other getOther() {
        return other;
    }

    public void setOther(Other other) {
        this.other = other;
    }

    public static class Other {
        @SerializedName("dream_world")
        private World dreamWorld;

        private Home home;

        @SerializedName("official_artwork")
        private Artwork officialArtwork;

        private FullImage showdown;

        public World getDreamWorld() {
            return dreamWorld;
        }

        public void setDreamWorld(World dreamWorld) {
            this.dreamWorld = dreamWorld;
        }

        public Home getHome() {
            return home;
        }

        public void setHome(Home home) {
            this.home = home;
        }

        public Artwork getOfficialArtwork() {
            return officialArtwork;
        }

        public void setOfficialArtwork(Artwork officialArtwork) {
            this.officialArtwork = officialArtwork;
        }

        public FullImage getShowdown() {
            return showdown;
        }

        public void setShowdown(FullImage showdown) {
            this.showdown = showdown;
        }

        public static class World implements FrontDefaultImaged, FrontFemaleImaged {
            @SerializedName("front_default")
            private String frontDefault;

            @SerializedName("front_female")
            private String frontFemale;

            @Override
            public String getFrontDefault() {
                return frontDefault;
            }

            @Override
            public void setFrontDefault(String frontDefault) {
                this.frontDefault = frontDefault;
            }

            @Override
            public String getFrontFemale() {
                return frontFemale;
            }

            @Override
            public void setFrontFemale(String frontFemale) {
                this.frontFemale = frontFemale;
            }
        }

        public static class Home implements
            FrontDefaultImaged,
            FrontFemaleImaged,
            FrontShinyImaged,
            FrontShinyFemaleImaged {
            @SerializedName("front_default")
            private String frontDefault;

            @SerializedName("front_female")
            private String frontFemale;

            @SerializedName("front_shiny")
            private String frontShiny;

            @SerializedName("front_shiny_female")
            private String frontShinyFemale;

            @Override
            public String getFrontDefault() {
                return frontDefault;
            }

            @Override
            public void setFrontDefault(String frontDefault) {
                this.frontDefault = frontDefault;
            }

            @Override
            public String getFrontFemale() {
                return frontFemale;
            }

            @Override
            public void setFrontFemale(String frontFemale) {
                this.frontFemale = frontFemale;
            }

            @Override
            public String getFrontShiny() {
                return frontShiny;
            }

            @Override
            public void setFrontShiny(String frontShiny) {
                this.frontShiny = frontShiny;
            }

            @Override
            public String getFrontShinyFemale() {
                return frontShinyFemale;
            }

            @Override
            public void setFrontShinyFemale(String frontShinyFemale) {
                this.frontShinyFemale = frontShinyFemale;
            }
        }

        public static class Artwork implements FrontDefaultImaged, FrontShinyImaged {
            @SerializedName("front_default")
            private String frontDefault;

            @SerializedName("front_shiny")
            private String frontShiny;

            @Override
            public String getFrontDefault() {
                return frontDefault;
            }

            @Override
            public void setFrontDefault(String frontDefault) {
                this.frontDefault = frontDefault;
            }

            @Override
            public String getFrontShiny() {
                return frontShiny;
            }

            @Override
            public void setFrontShiny(String frontShiny) {
                this.frontShiny = frontShiny;
            }
        }
    }
}
