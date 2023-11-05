package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.dto.RoomDTO;
import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final ItemService itemService;

    public RoomService(RoomRepository roomRepository, ItemService itemRepository) {
        this.roomRepository = roomRepository;
        this.itemService = itemRepository;

    }

    public List<RoomEntity> getRooms(UserEntity user) {
        return user.getRooms();
    }

    public List<RoomEntity> getRooms() {
        return roomRepository.findAll();
    }

    public void addRoom(RoomEntity newRoom) {
        roomRepository.save(newRoom);
    }

    public RoomEntity getRoom(Long roomId) {
        return roomRepository.findRoomById(roomId);
    }


    public RoomEntity createRoom(UserEntity host, String roomName) {

        RoomEntity room = new RoomEntity(roomName, host);
        room.addUser(host);
        roomRepository.save(room);

        List<ItemEntity> availableItems = itemService.getAll();
        room.setItems(availableItems);

        roomRepository.save(room);
        return room;
    }
    public RoomEntity updateRoom(Long id, RoomEntity updatedRoom){
        Optional<RoomEntity> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()){
            RoomEntity existingRoom = optionalRoom.get();
            existingRoom.setHeroes(updatedRoom.getHeroes());
            existingRoom.setUsers(updatedRoom.getUsers());
            existingRoom.setItems(updatedRoom.getItems());

            return roomRepository.save(existingRoom);
        }
        else{
            return null;
        }
    }
}
