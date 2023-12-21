package com.gloomhaven.helper.model.dto.rest.auth;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.RacesEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeroesResponse {
    private Long id;
    private String heroName;
    private RacesEnum race;
    private int xp;
    private int gold;
    private int progress;

    private String owner;

    public HeroesResponse(HeroEntity hero) {
        this.id = hero.getId();
        this.heroName = hero.getName();
        this.race = hero.getRace();
        this.xp = hero.getXp();
        this.gold = hero.getGold();
        this.progress = hero.getProgressPoints();
        this.owner = hero.getUser().getNickname();
    }
}
