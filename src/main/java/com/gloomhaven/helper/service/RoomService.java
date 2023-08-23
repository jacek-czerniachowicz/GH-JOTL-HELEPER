package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomEntity> getRooms() {
        return roomRepository.findAll();
    }

    public void saveRoom(RoomEntity newRoom) {
        roomRepository.save(newRoom);
    }

    public RoomEntity getRoom(Long roomId) {
        return roomRepository.findRoomById(roomId);
    }
}
