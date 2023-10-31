package com.gloomhaven.helper.model.dto;

import com.gloomhaven.helper.model.entities.*;

import java.util.ArrayList;

public class CreateHeroDTO {
    private final String name;
    private final Races race;
    private final UserEntity user;
    private final RoomEntity room;

    public CreateHeroDTO(String name, Races race, UserEntity user, RoomEntity room) {
        this.name = name;
        this.race = race;
        this.user = user;
        this.room = room;
    }
    public HeroEntity toHeroEntity(){
        HeroEntity heroEntity = new HeroEntity();
        heroEntity.setName(name);
        heroEntity.setRace(race);
        heroEntity.setUser(user);
        heroEntity.setRoom(room);
        heroEntity.setXp(0);
        heroEntity.setGold(0);
        heroEntity.setProgressPoints(0);
        heroEntity.setItems(new ArrayList<>());
        heroEntity.setPerks(new ArrayList<>());
        heroEntity.setCards(new ArrayList<>());

        return heroEntity;
    }
}
