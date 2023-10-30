package com.gloomhaven.helper.controller;

import com.gloomhaven.helper.model.dto.RoomDTO;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.service.RoomService;
import com.gloomhaven.helper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;
    private final UserService userService;

    @Autowired
    public RoomController(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    @GetMapping("")
    public String showRooms(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserEntity currentUser = userService.findByUsername(userDetails.getUsername());

        List<RoomEntity> availableRooms = currentUser.getRooms();
        model.addAttribute("availableRooms", availableRooms);

        List<RoomEntity> allRooms = roomService.getAllRooms();
        model.addAttribute("allRooms", allRooms);

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
        model.addAttribute("newRoom", new RoomDTO());
        return "add_room_form";
    }

    @PostMapping("/save")
    public String saveRoom(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute("newRoom") RoomDTO roomDTO){
        UserEntity host = userService.findByUsername(userDetails.getUsername());
        roomService.createRoom(host, roomDTO.getName());

        return "redirect:/rooms";
    }
}
