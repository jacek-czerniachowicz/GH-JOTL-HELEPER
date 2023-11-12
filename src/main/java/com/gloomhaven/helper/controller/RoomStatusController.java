package com.gloomhaven.helper.controller;

import com.gloomhaven.helper.model.dto.RoomStatusMessage;
import com.gloomhaven.helper.service.RoomService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class RoomStatusController {
    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;

    public RoomStatusController(RoomService roomService, SimpMessagingTemplate messagingTemplate) {
        this.roomService = roomService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/room-status")
    @SendTo("/topic/room-status")
    public RoomStatusMessage handleRoomStatus(RoomStatusMessage message) {
        RoomStatusMessage updatedMessage = checkAndUpdateRoomStatus(message);

        if (updatedMessage.isAllUsersReady()) {
            messagingTemplate.convertAndSend("/topic/redirect-to-game", "redirect");
        }
        return updatedMessage;
    }

    private RoomStatusMessage checkAndUpdateRoomStatus(RoomStatusMessage message) {
        if (message.getHeroIdReady() != null) {
            roomService.setHeroReady(message.getRoomId(), message.getHeroIdReady());
        }

        boolean allUsersReady = roomService.isAllPlayersReady(message.getRoomId());
        message.setAllUsersReady(allUsersReady);
        return message;
    }
}
