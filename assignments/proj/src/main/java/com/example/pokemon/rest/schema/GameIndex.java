package com.example.pokemon.rest.schema;

import com.google.gson.annotations.SerializedName;

public class GameIndex {
    @SerializedName("game_index")
    private int gameIndex;

    private NamedUrl version;

    public int getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(int gameIndex) {
        this.gameIndex = gameIndex;
    }

    public NamedUrl getVersion() {
        return version;
    }

    public void setVersion(NamedUrl version) {
        this.version = version;
    }
}
