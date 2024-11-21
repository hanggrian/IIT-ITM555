package com.example.pokemon.rest.schema;

public class Type {
    private int slot;
    private NamedUrl type;

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public NamedUrl getType() {
        return type;
    }

    public void setType(NamedUrl type) {
        this.type = type;
    }
}
