package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final ItemService itemService;
    private final HeroService heroService;

    public RoomService(RoomRepository roomRepository, ItemService itemRepository, HeroService heroService) {
        this.roomRepository = roomRepository;
        this.itemService = itemRepository;

        this.heroService = heroService;
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

        //FIXME: Make sure that method correctly initialize room with items
        RoomEntity newRoom = new RoomEntity(roomName, host);

        newRoom.addUser(host);
        newRoom.setItems(itemService.createItemsForRoom(newRoom));

        roomRepository.save(newRoom);
        return newRoom;
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

    public void setHeroReady(Long roomId, Long heroIdReady) {
        RoomEntity room = roomRepository.findRoomById(roomId);
        List<Long> heroesReadyId = room.getHeroesReadyId();
        heroesReadyId.add(heroIdReady);
        roomRepository.save(room);
    }

    public boolean isAllPlayersReady(Long roomId) {
        RoomEntity room = roomRepository.findRoomById(roomId);
        List<Long> heroesIdByRoomId = heroService.getHeroesByRoomId(roomId)
                .stream().map(HeroEntity::getId).toList();
        List<Long> heroesReadyId = room.getHeroesReadyId();
        return heroesReadyId.containsAll(heroesIdByRoomId);
    }
}
