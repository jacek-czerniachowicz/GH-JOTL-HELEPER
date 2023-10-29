package com.gloomhaven.helper.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomDTO {
    private String name;
    private String hostUsername;

    public RoomDTO(String name, String username) {
        this.name = name;
        this.hostUsername = username;
    }
}
