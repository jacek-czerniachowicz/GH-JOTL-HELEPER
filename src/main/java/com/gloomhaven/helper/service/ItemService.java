package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.ItemEnum;
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

    public List<ItemEntity> createItemsForRoom() {

        List<ItemEntity> roomItems = new ArrayList<>();
        for (ItemEnum itemData: ItemEnum.values()) {
            roomItems.add(new ItemEntity(itemData));
        }
//        List<ItemEntity> roomItems = new ArrayList<>(List.of(
//                new ItemEntity("buty szybkości", room, "zwiększa zasięg ruchu o 5",10,  2),
//                new ItemEntity("buty lekkości", room, "dodaj skok do całej akcji ruchu",15,  1),
//                new ItemEntity("miecz gerarta", room, "utopce robią brr",999,  5)
//        ));

        return roomItems;
    }

//    public void update(ItemEntity updatedItem){
//        ItemEntity item = getItem(updatedItem.getId());
//        item.setRooms(updatedItem.getRooms());
//        itemRepository.save(item);
//    }


}