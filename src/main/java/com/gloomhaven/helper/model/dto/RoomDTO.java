package com.gloomhaven.helper.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomDTO {
    private String name;

    public RoomDTO(String name) {
        this.name = name;
    }
}
