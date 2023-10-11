package com.gloomhaven.helper.controller;

import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("")
    public String showRooms(Model model) {
        List<RoomEntity> availableRooms = roomService.getRooms();
        model.addAttribute("availableRooms", availableRooms);
        return "room_choosing_menu";
    }

    @GetMapping("/room")
    public String showRoom(@RequestParam("roomId") Long roomId, Model model) {
        RoomEntity room = roomService.getRoom(roomId);
        model.addAttribute("room", room);
        return "room";
    }
    @GetMapping("/add")
    public String addRoom(Model model){
        model.addAttribute("newRoom", new RoomEntity());
        return "add_room_form";
    }

    @PostMapping("/save")
    public String saveRoom(@ModelAttribute RoomEntity newRoom){
        roomService.saveRoom(newRoom);
        return "redirect:/rooms";
    }
}
