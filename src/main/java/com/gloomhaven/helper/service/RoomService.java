package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.dto.RoomDTO;
import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.RoomItemEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.repository.ItemRepository;
import com.gloomhaven.helper.repository.ItemRepository;
import com.gloomhaven.helper.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final ItemRepository itemRepository;

    public RoomService(RoomRepository roomRepository, ItemRepository itemRepository) {
        this.roomRepository = roomRepository;
        this.itemRepository = itemRepository;
        this.itemRepository = itemRepository;
    }

    public List<RoomEntity> getRooms(UserEntity user) {
        return user.getRooms();
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

    public RoomEntity createRoom(UserEntity host, String roomName) {

        RoomEntity room = new RoomEntity(roomName, host);
        room.addUser(host);

        //create RoomItemEntities
        List<RoomItemEntity> roomItems = new ArrayList<>();
        for (ItemEntity item : itemRepository.findAll()) {
            roomItems.add(new RoomItemEntity(room,item));
        }
        room.setRoomItems(roomItems);

        roomRepository.save(room);
        return room;
    }

    public void initializeRoomWithItems(RoomEntity room){
        List<ItemEntity> availableItems = itemRepository.findAll();

        List<ItemEntity> roomItems = new ArrayList<>(availableItems);
        room.setItems(roomItems);
        roomRepository.save(room);
    }
}
