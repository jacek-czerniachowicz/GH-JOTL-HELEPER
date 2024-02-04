package com.gloomhaven.helper.model.dto;

import com.gloomhaven.helper.model.dto.rest.CreateHeroRequest;
import com.gloomhaven.helper.model.entities.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateHeroDTO {
    private String name;
    private RacesEnum race;
    private UserEntity user;
    private RoomEntity room;
    private Long roomId;

    public CreateHeroDTO(CreateHeroRequest request){
        this.name = request.getName();
        this.race = RacesEnum.valueOf(request.getRaceName());
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
