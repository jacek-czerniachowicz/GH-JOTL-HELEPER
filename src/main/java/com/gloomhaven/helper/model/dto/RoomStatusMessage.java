package com.gloomhaven.helper.model.dto;

public class RoomStatusMessage {

    private Long roomId;
    private Long heroIdReady;
    private boolean allUsersReady;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getHeroIdReady() {
        return heroIdReady;
    }

    public void setHeroIdReady(Long heroIdReady) {
        this.heroIdReady = heroIdReady;
    }

    public boolean isAllUsersReady() {
        return allUsersReady;
    }

    public void setAllUsersReady(boolean allUsersReady) {
        this.allUsersReady = allUsersReady;
    }
}