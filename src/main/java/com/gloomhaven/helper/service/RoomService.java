package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.dto.RoomDTO;
import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.RoomItemEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
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
    private final UserService userService;

    @Autowired
    public RoomService(RoomRepository roomRepository, ItemRepository itemRepository, UserService userService) {
        this.roomRepository = roomRepository;
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    public List<RoomEntity> getRooms(UserEntity user) {
        return user.getRooms();
    }

    public void saveRoom(RoomEntity newRoom) {
        roomRepository.save(newRoom);
    }

    public RoomEntity getRoom(Long roomId) {
        return roomRepository.findRoomById(roomId);
    }

    public RoomEntity createRoom(UserEntity host, RoomDTO roomDTO) {

        System.out.println("from create room");

        RoomEntity room = new RoomEntity(roomDTO.getName(), host);
        room.addUser(host);

        //create RoomItemEntities
        List<RoomItemEntity> roomItems = new ArrayList<>();
        for (ItemEntity item : itemRepository.findAll()) {
            roomItems.add(new RoomItemEntity(room,item));
        }
        room.setRoomItems(roomItems);

        for (RoomItemEntity item : room.getRoomItems()){
            System.out.println(item);
        }
        System.out.println("after create room");

        roomRepository.save(room);
        return room;
    }
}
