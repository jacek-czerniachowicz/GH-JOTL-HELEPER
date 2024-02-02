package com.gloomhaven.helper.model.dto.rest;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.RacesEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeroResponse {
    private Long id;
    private String name;
    private String race;
    private int xp;
    private int progressPoints;
    private int level;
    private int gold;
    private int deckSize;

    private List<ItemResponse> items;
    private List<PerkResponse> perks;

    public HeroResponse(HeroEntity hero) {
        this.id = hero.getId();
        this.name = hero.getName();
        this.race = hero.getRace().getName();
        this.xp = hero.getXp();
        this.progressPoints = hero.getProgressPoints();
        this.level = hero.getLevel();
        this.gold = hero.getGold();
        this.deckSize = hero.getDeckSize();

        this.items = hero.getItems().stream().map(ItemResponse::new).toList();
        this.perks = hero.getPerks().stream().map(PerkResponse::new).toList();
    }
}
