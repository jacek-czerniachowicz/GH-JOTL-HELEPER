package com.gloomhaven.helper.model.entities;

public enum RacesEnum {
    VOIDWARDEN("Voidwarden"),
    REDGUARD("Redguard"),
    HATCHET("Hatchet"),
    DEMOLITIONIST("Demolitionist");

    private String name;
    RacesEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
