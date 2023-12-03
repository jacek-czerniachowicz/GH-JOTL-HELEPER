package com.gloomhaven.helper.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomStatusMessage {
    private Long roomId;
    private Long heroIdReady;
    private String heroName;
    private boolean allUsersReady;

}