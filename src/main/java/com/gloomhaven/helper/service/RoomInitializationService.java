package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.RoomItemEntity;
import com.gloomhaven.helper.repository.ItemRepository;
import com.gloomhaven.helper.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomInitializationService {
    private final ItemRepository itemRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public RoomInitializationService(ItemRepository itemRepository, RoomRepository roomRepository) {
        this.itemRepository = itemRepository;
        this.roomRepository = roomRepository;
    }

    public void initializeRoomWithItems(RoomEntity room){
        List<ItemEntity> availableItems = itemRepository.findAll();
        List<RoomItemEntity> roomItems = new ArrayList<>();

        for (ItemEntity item : availableItems){
            roomItems.add(new RoomItemEntity(room, item));
        }
        room.setRoomItems(roomItems);
        roomRepository.save(room);
    }
}
