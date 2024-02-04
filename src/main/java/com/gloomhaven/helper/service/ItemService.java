package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.ItemEnum;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.repository.HeroRepository;
import com.gloomhaven.helper.repository.ItemRepository;
import com.gloomhaven.helper.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final HeroRepository heroRepository;
    private final RoomRepository roomRepository;

    public ItemEntity buyItem(long roomId, long itemId, String userEmail) throws Exception {
        HeroEntity hero = getHero(roomId, userEmail);
        ItemEntity item = getItem(itemId);
        RoomEntity room = getRoom(roomId);

        validatePurchase(hero, item, room);

        executePurchase(hero, item);

        return item;
    }

    public ItemEntity equipItem(long roomId, long itemId, String username) throws Exception {
        HeroEntity hero = getHero(roomId, username);
        ItemEntity item = getItem(itemId);

        validateOwnership(hero, item);

        equipItemBasedOnType(hero, item);

        heroRepository.save(hero);
        return item;
    }

    public List<ItemEntity> getAvailableItems(long roomId) {
        return itemRepository.findAllByRoomIdAndHeroIsNull(roomId);
    }

    public List<ItemEntity> getHeroItems(long roomId, String userEmail) {
        try {
            return getHero(roomId, userEmail).getItems();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private ItemEntity getItem(long itemId) throws Exception {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new Exception("Item not found"));
    }

    private HeroEntity getHero(long roomId, String userEmail) throws Exception {
        return heroRepository.findByRoomIdAndUserEmail(roomId, userEmail)
                .orElseThrow(() -> new Exception("Hero not found"));
    }

    private RoomEntity getRoom(long roomId) {
        return roomRepository.findRoomById(roomId);
    }

    private void validatePurchase(HeroEntity hero, ItemEntity item, RoomEntity room) throws Exception {
        if (!room.getItems().contains(item) || item.getHero() != null) {
            throw new Exception("Invalid item ID");
        }
        if (hero.getGold() < item.getItemData().getPrice()) {
            throw new Exception("Not enough gold");
        }
    }

    private void executePurchase(HeroEntity hero, ItemEntity item) {
        hero.setGold(hero.getGold() - item.getItemData().getPrice());
        item.setHero(hero);
        heroRepository.save(hero);
    }

    private void validateOwnership(HeroEntity hero, ItemEntity item) throws Exception {
        if (!hero.getItems().contains(item)) {
            throw new Exception("The hero does not have such an item");
        }
    }

    private void equipItemBasedOnType(HeroEntity hero, ItemEntity item) {
        ItemEnum.ItemType itemType = item.getItemData().getType();
        List<ItemEntity> equippedItems = hero.getItems().stream()
                .filter(ItemEntity::isEquipped)
                .toList();

        if (itemType.equals(ItemEnum.ItemType.WEAPON)) {
            equipWeapon(item, equippedItems);
        } else {
            equipNonWeapon(item, equippedItems);
        }
    }

    private void equipWeapon(ItemEntity item, List<ItemEntity> equippedItems) {
        List<ItemEntity> equippedWeapons = equippedItems.stream()
                .filter(equippedItem -> equippedItem.getItemData().getType().equals(ItemEnum.ItemType.WEAPON))
                .toList();

        if (equippedWeapons.size() >= 2) {
            equippedWeapons.get(0).changeEquip();
        }

        item.changeEquip();
    }

    private void equipNonWeapon(ItemEntity item, List<ItemEntity> equippedItems) {
        equippedItems.stream()
                .filter(equippedItem -> equippedItem.getItemData().getType().equals(item.getItemData().getType()))
                .findFirst()
                .ifPresent(ItemEntity::changeEquip);

        item.changeEquip();
    }

    public List<ItemEntity> createItemsForRoom(RoomEntity room) {
        List<ItemEntity> roomItems = new ArrayList<>();
        for (ItemEnum itemData : ItemEnum.values()) {
            roomItems.add(new ItemEntity(itemData, room));
        }
        return roomItems;
    }
}