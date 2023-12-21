package com.gloomhaven.helper.model.entities;

public enum RacesEnum {
    VOIDWARDEN("Pustowicielka"),
    REDGUARD("Czerwony strażnik"),
    HATCHET("Topór"),
    DEMOLITIONIST("Wyburzyciel(?)");

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
