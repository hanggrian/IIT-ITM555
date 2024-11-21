package com.example.pokemon.rest.schema.image;

import com.google.gson.annotations.SerializedName;

public class FullImage implements
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
}
