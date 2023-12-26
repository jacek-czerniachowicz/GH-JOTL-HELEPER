package com.gloomhaven.helper.model.entities;

import lombok.Getter;

@Getter
public enum ItemEnum {
    BOOTS1("buty szybkości", "zwiększ zasięg ruchu o 5", 15, ItemType.BOOTS),
    BOOTS2("redbull", "dodaj skok do całej akcji ruchu", 30, ItemType.BOOTS),
    SWORD1("miecz geranda", "utopce robią brr", 420,ItemType.WEAPON),
    HELMET1("kask", "za darmo to uczciwa cena", 0, ItemType.HELMET);

    public enum ItemType {
        HELMET,
        BOOTS,
        CHEST,
        WEAPON,
        POTION
    }


    private final String name;
    private final String description;
    private final int price;
    private final ItemType type;

    ItemEnum(String name, String description, int price, ItemType type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }

}
