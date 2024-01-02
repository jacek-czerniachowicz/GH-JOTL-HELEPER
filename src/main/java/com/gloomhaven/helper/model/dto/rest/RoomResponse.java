package com.gloomhaven.helper.model.dto.rest;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    Long id;
    String roomName;
    int level;
    String host;


    List<String> users;
    List<String> heroes;

    public RoomResponse(RoomEntity room) {
        this.id = room.getId();
        this.roomName = room.getName();
        this.level = room.getCurrentLevel();
        this.host = room.getHost().getNickname();
        this.users = room.getUsers().stream().map(UserEntity::getNickname).toList();
        this.heroes = room.getHeroes().stream().map(HeroEntity::getName).toList();
    }
}
