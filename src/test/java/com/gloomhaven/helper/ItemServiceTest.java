package com.gloomhaven.helper;

import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
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
        ItemEntity newItem = new ItemEntity("item",room, "description",1,  1);
        //when
        itemService.addItem(newItem);
        ItemEntity foundedItem = itemService.getItem(newItem.getId());
        //then
        Assertions.assertEquals(foundedItem.getName(), newItem.getName());
    }

    @Test
    void shouldAddCollectionOfItems(){
        //before
        ItemEntity newItem = new ItemEntity("item1", room, "description1", 1, 1);
        ItemEntity newItem2 = new ItemEntity("item2", room, "description2", 1,  2);
        ItemEntity newItem3 = new ItemEntity("item3", room, "description3", 1, 3);

        //when
        itemService.addItems(List.of(newItem, newItem2, newItem3));
        ItemEntity foundedItem = itemService.getItem(newItem2.getId());
        ItemEntity foundedItem2 = itemService.getItem(newItem3.getId());
        //then
        Assertions.assertEquals(foundedItem.getDescription(), newItem2.getDescription());
        Assertions.assertEquals(foundedItem2.getName(), newItem3.getName());

    }
}

