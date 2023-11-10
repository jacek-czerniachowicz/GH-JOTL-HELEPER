package com.gloomhaven.helper;

import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.ItemEnum;
import com.gloomhaven.helper.service.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ItemServiceTest {
    @Autowired
    ItemService itemService;
    RoomEntity room = new RoomEntity();

    @Test
    void shouldAddItem(){
        //before
        ItemEntity newItem = new ItemEntity(ItemEnum.BOOTS2);
        //when
        itemService.addItem(newItem);
        ItemEntity foundedItem = itemService.getItem(newItem.getId());
        //then
        Assertions.assertEquals(foundedItem.getItemData().getName(), newItem.getItemData().getName());
    }

    @Test
    void shouldAddCollectionOfItems(){
        //before
        ItemEntity newItem = new ItemEntity(ItemEnum.BOOTS1);
        ItemEntity newItem2 = new ItemEntity(ItemEnum.BOOTS2);
        ItemEntity newItem3 = new ItemEntity(ItemEnum.SWORD1);

        //when
        itemService.addItems(List.of(newItem, newItem2, newItem3));
        ItemEntity foundedItem = itemService.getItem(newItem2.getId());
        ItemEntity foundedItem2 = itemService.getItem(newItem3.getId());
        //then
        Assertions.assertEquals(foundedItem.getItemData().getDescription(), newItem2.getItemData().getDescription());
        Assertions.assertEquals(foundedItem2.getItemData().getName(), newItem3.getItemData().getName());

    }
}

