package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final ItemService itemService;

    @Autowired
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

    public RoomEntity getRoom(String roomName){
        return roomRepository.findRoomByName(roomName);
    }


    public void createRoom(UserEntity host, String roomName) {

        //FIXME: Make sure that method correctly initialize room with items
        RoomEntity newRoom = new RoomEntity(roomName, host);

        newRoom.setItems(itemService.createItemsForRoom(newRoom));
        host.addHostedRoom(newRoom);

        roomRepository.save(newRoom);
    }
    public RoomEntity updateRoom(RoomEntity updatedRoom){
        Optional<RoomEntity> optionalRoom = roomRepository.findById(updatedRoom.getId());
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

    public void addUserToRoom(RoomEntity room, UserEntity user) {
        room.addUser(user);
        user.addRoom(room);
        roomRepository.save(room);
    }
}
