package com.gloomhaven.helper.model.entities;

public enum ItemEnum {
    BOOTS1("buty szybkości", "zwiększ zasięg ruchu o 5", 15),
    BOOTS2("redbull", "dodaj skok do całej akcji ruchu", 30),
    SWORD1("miecz geranda", "utopce robią brr", 420);

    private final String name;
    private final String description;
    private final int price;

    ItemEnum(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
