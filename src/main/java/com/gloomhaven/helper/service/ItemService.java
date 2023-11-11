package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.ItemEnum;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public List<ItemEntity> getAll(){
        return itemRepository.findAll();
    }
    @Transactional
    public ItemEntity getItem(long id){
        return itemRepository.findAllById(id);
    }
    @Transactional
    public void addItem(ItemEntity item){
        itemRepository.save(item);
    }
    @Transactional
    public void addItems(Iterable<ItemEntity> collection){
        itemRepository.saveAll(collection);
    }

    public List<ItemEntity> createItemsForRoom(RoomEntity room) {

        List<ItemEntity> roomItems = new ArrayList<>();
        for (ItemEnum itemData: ItemEnum.values()) {
            roomItems.add(new ItemEntity(itemData, room));
        }
        return roomItems;
    }

    public void buyItem(ItemEntity item, HeroEntity hero) {
        item.setHero(hero);
        itemRepository.save(item);
    }

    public List<ItemEntity> getAvailableItems(RoomEntity room) {
        return itemRepository.findAllByRoomAndHeroIsNull(room);
    }

}