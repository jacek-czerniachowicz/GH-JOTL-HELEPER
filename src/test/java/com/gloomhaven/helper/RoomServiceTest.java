package com.gloomhaven.helper;

import com.gloomhaven.helper.model.dto.UserDTO;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.service.ItemService;
import com.gloomhaven.helper.service.RoomService;
import com.gloomhaven.helper.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Test
    void createRoomTest() {
        //before
        String newRoomName = "testNewRoom";
        UserDTO userDTO = new UserDTO("testEmail", "testName", "testPassword");
        userService.createUser(userDTO);
        List<RoomEntity> beforeRoomList = roomService.getRooms();

        //when
        UserEntity foundedUser = userService.findByUsername(userDTO.getUsername());
        roomService.createRoom(foundedUser, newRoomName);
        RoomEntity foundedRoom = roomService.getRoom(newRoomName);
        UserEntity foundedUserAfter = userService.findByUsername(userDTO.getUsername());

        //then
        Assertions.assertNotEquals(roomService.getRooms(), beforeRoomList);
        Assertions.assertEquals(foundedRoom.getName(), newRoomName);
        Assertions.assertEquals(foundedRoom.getHost().getUsername(),userDTO.getUsername());
        Assertions.assertEquals(foundedUser, foundedUserAfter);
        Assertions.assertEquals(foundedUserAfter.getHostedRooms().get(0), foundedRoom);
    }

    @Test
    void shouldAddUserToRoom() {
        //before
        //create room hosted by root
        UserEntity root = userService.findByUsername("root");
        String roomName = "roomName";
        roomService.createRoom(root,roomName);
        //create user to add
        UserDTO userDTO = new UserDTO("userEmail", "userName", "password");
        userService.createUser(userDTO);

        List<UserEntity> usersBefore = new ArrayList<>(roomService.getRoom(roomName).getUsers());

        //after
        RoomEntity room = roomService.getRoom(roomName);
        UserEntity addedUser = userService.findByUsername(userDTO.getUsername());
        roomService.addUserToRoom(room, addedUser);

        //then
        Assertions.assertNotEquals(usersBefore,room.getUsers());

        List<RoomEntity> userRooms = userService.findByUsername(userDTO.getUsername()).getRooms();
        boolean founded = false;
        for (RoomEntity userRoom : userRooms) {
            if (Objects.equals(userRoom.getId(), room.getId())) {
                founded = true;
                break;
            }
        }
        Assertions.assertTrue(founded, "Not found demanded room in userRooms");
    }
}


