package com.gloomhaven.helper;

import com.gloomhaven.helper.model.dto.UserDTO;
import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.service.ItemService;
import com.gloomhaven.helper.service.RoomService;
import com.gloomhaven.helper.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserServiceImpl userService;

//    @Test
//    public void testCreateRoom() {
//        //before
//        ItemEntity item1 = new ItemEntity("item1", "description1", 1, 1);
//        ItemEntity item2 = new ItemEntity("item2", "description2", 1, 2);
//
//        UserDTO user = new UserDTO();
//        user.setEmail("email@g.com");
//        user.setUsername("testHost");
//        user.setPassword("password");
//
//        String roomName = "Test Room";
//
//        //when
//        itemService.addItems(List.of(item1, item2));
//        userService.createUser(user);
//        UserEntity host = userService.findByUsername("testHost");
//        RoomEntity createdRoom = roomService.createRoom(host, roomName);
//        List<ItemEntity> availableItems = itemService.getAll();
//
//        //then
//        assertEquals(roomName, createdRoom.getName());
//        assertEquals(host.getId(), createdRoom.getHost().getId());
//        assertEquals(availableItems.size(), createdRoom.getItems().size());
//        assertThat(item1).isIn(createdRoom.getItems());
//        assertThat(item2).isIn(createdRoom.getItems());
//    }
}
