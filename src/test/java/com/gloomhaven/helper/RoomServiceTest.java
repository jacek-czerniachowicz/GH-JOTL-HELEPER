package com.gloomhaven.helper;

import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.repository.ItemRepository;
import com.gloomhaven.helper.repository.RoomRepository;
import com.gloomhaven.helper.service.ItemService;
import com.gloomhaven.helper.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ItemService itemService;

    @Test
    public void testCreateRoomWithItems() {
        // Create a sample user and items
        UserEntity user = new UserEntity("john@email.com", "John", "pwd");
        ItemEntity item1 = new ItemEntity("Item 1", "Description 1", 1);
        ItemEntity item2 = new ItemEntity("Item 2", "Description 2", 2);

        // Mock the behavior of itemService
        when(itemService.getAll()).thenReturn(Arrays.asList(item1, item2));

        // Create a room with items
        RoomEntity room = roomService.createRoom(user, "Room 1");

        // Verify that roomRepository.save() was called twice
        verify(roomRepository, times(2)).save(any());

        // Verify that the room has the correct items
        assertThat(room.getName()).isEqualTo("Room 1");
        assertThat(room.getHost()).isEqualTo(user);
        assertThat(room.getItems()).contains(item1, item2);
    }
}
