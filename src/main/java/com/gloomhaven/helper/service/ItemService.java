package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.ItemEnum;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.repository.HeroRepository;
import com.gloomhaven.helper.repository.ItemRepository;
import com.gloomhaven.helper.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final HeroRepository heroRepository;
    private final RoomRepository roomRepository;

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
    public ItemEntity buyItem(long roomId, long itemId, String userEmail) throws Exception {
        HeroEntity hero = heroRepository.findByRoomIdAndUserEmail(roomId, userEmail).orElseThrow();
        ItemEntity item = itemRepository.findById(itemId).orElseThrow();
        RoomEntity room = roomRepository.findRoomById(roomId);

        if(!room.getItems().contains(item) || item.getHero() != null){
            throw new Exception("bad id");
        }
        if (!(hero.getGold() >= item.getItemData().getPrice())){
            throw new Exception("not enough gold");
        }

        hero.setGold(hero.getGold() - item.getItemData().getPrice());
        item.setHero(hero);
        heroRepository.save(hero);

        return item;
    }



    public List<ItemEntity> getAvailableItems(RoomEntity room) {
        return itemRepository.findAllByRoomAndHeroIsNull(room);
    }

    public List<ItemEntity> getAvailableItems(long roomId) {
        return itemRepository.findAllByRoomIdAndHeroIsNull(roomId);
    }

    public ItemEntity equipItem(long roomId, long itemId, String username) throws Exception {
        ItemEntity item = itemRepository.findById(itemId).orElseThrow();
        HeroEntity hero = heroRepository.findByRoomIdAndUserEmail(roomId, username).orElseThrow();

        if (!hero.getItems().contains(item)){
            throw new Exception("the hero does not have such an item");
        }

        ItemEnum.ItemType itemType = item.getItemData().getType();

        List<ItemEntity> items = hero.getItems();
        List<ItemEntity> equippedItems = items.stream().filter(ItemEntity::isEquipped).toList();

        if (itemType.equals(ItemEnum.ItemType.WEAPON)) {
            List<ItemEntity> equippedWeapons = equippedItems.stream().filter(
                    equippedItem -> equippedItem.getItemData().getType().equals(ItemEnum.ItemType.WEAPON)).toList();
            if (equippedWeapons.size() >= 2){
                equippedWeapons.get(0).changeEquip();
            }
            item.changeEquip();

        }else {
            for (ItemEntity equippedItem : equippedItems) {
                if (equippedItem.getItemData().getType().equals(itemType)) {
                    equippedItem.changeEquip();
                    break;
                }
            }
            item.changeEquip();
        }

        heroRepository.save(hero);
        return item;
    }

    public List<ItemEntity> getHeroItems(long roomId, String username) {
        HeroEntity hero = heroRepository.findByRoomIdAndUserEmail(roomId, username).orElseThrow();
        return hero.getItems();
    }
}