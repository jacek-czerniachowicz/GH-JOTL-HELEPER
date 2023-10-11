package com.gloomhaven.helper.model.entities;

public enum Races {
    VOIDWARDEN("Voidwarden"),
    REDGUARD("Redguard"),
    HATCHET("Hatchet"),
    DEMOLITIONIST("Demolitionist");

    private String name;
    Races(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
