package com.gloomhaven.helper.model.entities;

import lombok.Getter;

import static com.gloomhaven.helper.model.entities.ItemEnum.ItemType.*;

@Getter
public enum ItemEnum {
    BOOTS1("buty szybkości", "zwiększ zasięg ruchu o 5", 15, BOOTS, ""),
    BOOTS2("redbull", "dodaj skok do całej akcji ruchu", 30, BOOTS, ""),
    SWORD1("miecz geranda", "utopce robią brr", 420, WEAPON, ""),
    HELMET1("kask", "za darmo to uczciwa cena", 0, HELMET, "");

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
    private final String imgUrl;

    ItemEnum(String name, String description, int price, ItemType type, String imgUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.imgUrl = imgUrl;
    }

}
