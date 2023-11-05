package com.gloomhaven.helper.controller;

import com.gloomhaven.helper.model.dto.CreateHeroDTO;
import com.gloomhaven.helper.model.dto.RoomDTO;
import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.service.CreateHeroService;
import com.gloomhaven.helper.service.HeroService;
import com.gloomhaven.helper.service.RoomService;
import com.gloomhaven.helper.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;
    private final UserService userService;
    private final HeroService heroService;
    private CreateHeroService createHeroService;


    @Autowired
    public RoomController(RoomService roomService, UserService userService, HeroService heroService, CreateHeroService createHeroService) {
        this.roomService = roomService;
        this.userService = userService;
        this.heroService = heroService;
        this.createHeroService = createHeroService;
    }

    @GetMapping("")
    public String showRooms(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserEntity currentUser = userService.findByUsername(userDetails.getUsername());

        List<RoomEntity> availableRooms = currentUser.getRooms();
        model.addAttribute("availableRooms", availableRooms);

        List<RoomEntity> allRooms = roomService.getRooms();
        model.addAttribute("allRooms", allRooms);

        return "room_choosing_menu";
    }

    @GetMapping("/room")
    public String showRoom(@RequestParam("roomId") Long roomId, Model model,
                            @AuthenticationPrincipal UserDetails userDetails) {
        RoomEntity room = roomService.getRoom(roomId);
        UserEntity user = userService.findByUsername(userDetails.getUsername());

        HeroEntity hero = heroService.getUserHero(room.getId(), user.getId());

        model.addAttribute("hero", Objects.requireNonNullElseGet(hero, HeroEntity::new));
        model.addAttribute("room", room);
        return "room";
    }

    @GetMapping("/add")
    public String addRoom(Model model){
        model.addAttribute("newRoom", new RoomDTO());
        return "add_room_form";
    }

    @PostMapping("/save")
    public String saveRoom(@AuthenticationPrincipal UserDetails userDetails,
                           @ModelAttribute("newRoom") RoomDTO roomDTO){
        UserEntity host = userService.findByUsername(userDetails.getUsername());
        roomService.createRoom(host, roomDTO.getName());

        return "redirect:/rooms";
    }
    @GetMapping("room/create_new_hero")
    public String createHero(Model model,
                            @RequestParam("roomId") Long roomId){
        model.addAttribute("newHero", new CreateHeroDTO(roomId));
        return "create_hero_form";
    }
    @PostMapping("room/save_hero")
    public String saveHero(@AuthenticationPrincipal UserDetails userDetails,
                           @ModelAttribute("newHero") @NotNull CreateHeroDTO heroDTO){

        UserEntity user = userService.findByUsername(userDetails.getUsername());
        if (createHeroService.createHero(heroDTO, user)){
            return "redirect:/rooms/room?roomId=" + heroDTO.getRoom().getId();
        }
        else{
            //TODO: Make some error handling
            return "redirect:/rooms/room?roomId=" + heroDTO.getRoom().getId();
        }
    }
}
