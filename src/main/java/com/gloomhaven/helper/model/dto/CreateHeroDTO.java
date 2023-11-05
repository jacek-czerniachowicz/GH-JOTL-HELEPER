package com.gloomhaven.helper.model.dto;

import com.gloomhaven.helper.model.entities.*;
import lombok.Data;


import java.util.ArrayList;

@Data
public class CreateHeroDTO {
    private String name;
    private RacesEnum race;
    private UserEntity user;
    private RoomEntity room;

    public CreateHeroDTO() {
    }
    public CreateHeroDTO(String name, RacesEnum race, UserEntity user, RoomEntity room) {
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
