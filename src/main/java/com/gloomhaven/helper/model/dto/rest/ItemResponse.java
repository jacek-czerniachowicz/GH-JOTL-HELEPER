package com.gloomhaven.helper.model.dto.rest;

import com.gloomhaven.helper.model.entities.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {
    private long id;
    private String itemTypeEnum;
    private String name;
    private String description;
    private int price;
    private String imgUrl;
    private String heroName;

    public ItemResponse(ItemEntity item) {
        this.id = item.getId();
        this.itemTypeEnum = item.getItemData().getType().name();
        this.name = item.getItemData().getName();
        this.description = item.getItemData().getDescription();
        this.price = item.getItemData().getPrice();
        if(item.getHero() != null) {
            this.heroName = item.getHero().getName();
        }
        else this.heroName = "merchant";

    }
}
