package com.gloomhaven.helper.model.dto.rest;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private Long id;
    private String roomName;
    private int level;

    private UserResponse host;
    private List<UserResponse> users;
    private List<HeroResponse> heroes = new ArrayList<>();

    public RoomResponse(RoomEntity room) {
        this.id = room.getId();
        this.roomName = room.getName();
        this.level = room.getCurrentLevel();
        this.host = new UserResponse(room.getHost());
        this.users = room.getUsers().stream().map(UserResponse::new).toList();
        if (room.getHeroes() != null){
            this.heroes = room.getHeroes().stream().map(HeroResponse::new).toList();
        }

    }
}
