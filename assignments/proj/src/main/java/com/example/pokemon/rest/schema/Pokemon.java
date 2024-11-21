package com.example.pokemon.rest.schema;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Pokemon {
    private List<NamedUrl> forms;

    @SerializedName("game_indices")
    private List<GameIndex> gameIndices;

    private int height;
    private int id;
    private List<Move> moves;
    private String name;
    private NamedUrl species;
    private Sprites sprites;
    private List<Stat> stats;
    private List<Type> types;
    private int weight;

    public List<NamedUrl> getForms() {
        return forms;
    }

    public void setForms(List<NamedUrl> forms) {
        this.forms = forms;
    }

    public List<GameIndex> getGameIndices() {
        return gameIndices;
    }

    public void setGameIndices(List<GameIndex> gameIndices) {
        this.gameIndices = gameIndices;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NamedUrl getSpecies() {
        return species;
    }

    public void setSpecies(NamedUrl species) {
        this.species = species;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public List<Stat> getStats() {
        return stats;
    }

    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
