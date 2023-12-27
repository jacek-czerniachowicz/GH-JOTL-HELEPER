package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.repository.RoomRepository;
import com.gloomhaven.helper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;
    private final HeroService heroService;


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

    public void removeRoom(RoomEntity room) {
        roomRepository.delete(room);
    }


    public RoomEntity createRoom(UserEntity host, String roomName) {

        //FIXME: Make sure that method correctly initialize room with items
        RoomEntity newRoom = new RoomEntity(roomName, host);
        newRoom.setItems(itemService.createItemsForRoom(newRoom));
        return roomRepository.save(newRoom);
    }
    public RoomEntity createRoom(String roomName, String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow();
        return createRoom(user, roomName);

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

    public RoomEntity addUserToRoom(RoomEntity room, UserEntity user) {
        if(room.getUsers().contains(user)) throw new RuntimeException("User: " + user.getId() + " already is present in this room: " + room.getId());
        else {
            room.addUser(user);
            return roomRepository.save(room);
        }
    }

    public List<RoomEntity> getRooms(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow();
        return getRooms(user);
    }

    public RoomEntity joinToRoom(Long roomId, String userEmail){
        RoomEntity room = roomRepository.findRoomById(roomId);
        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow();
        return addUserToRoom(room, user);
    }

    public boolean delete(Long roomId){
        if (!roomRepository.existsById(roomId)){
            return false;
        }
        RoomEntity room = roomRepository.findRoomById(roomId);
        room.wipe();
        roomRepository.delete(room);
        return true;
    }


}
