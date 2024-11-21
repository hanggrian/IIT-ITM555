package com.example.pokemon.rest.schema;

import com.google.gson.annotations.SerializedName;

public class Stat {
    @SerializedName("base_stat")
    private int baseStat;

    private int effort;

    private NamedUrl stat;

    public int getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(int baseStat) {
        this.baseStat = baseStat;
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }

    public NamedUrl getStat() {
        return stat;
    }

    public void setStat(NamedUrl stat) {
        this.stat = stat;
    }
}
