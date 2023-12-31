package com.gloomhaven.helper.controller;

import com.gloomhaven.helper.model.dto.CreateHeroDTO;
import com.gloomhaven.helper.model.dto.RoomDTO;
import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.service.*;
import jakarta.transaction.Transactional;
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
    private final ItemService itemService;
    private CreateHeroService createHeroService;


    @Autowired
    public RoomController(RoomService roomService, UserService userService, HeroService heroService, ItemService itemService, CreateHeroService createHeroService) {
        this.roomService = roomService;
        this.userService = userService;
        this.heroService = heroService;
        this.itemService = itemService;
        this.createHeroService = createHeroService;
    }

    @GetMapping("")
    public String showRooms(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserEntity currentUser = userService.findByUsername(userDetails.getUsername());

        List<RoomEntity> availableRooms = currentUser.getRooms();
        model.addAttribute("availableRooms", availableRooms);

        List<RoomEntity> allRooms = roomService.getRooms();
        model.addAttribute("allRooms", allRooms);
        model.addAttribute("user", currentUser);

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

    @Transactional
    @PostMapping("/add_user_to_room")
    public String addUser(@RequestParam("roomId") Long roomId, @RequestParam("userId") Long userId) {

        UserEntity user = userService.findUser(userId);
        RoomEntity room = roomService.getRoom(roomId);

        if (room.getUsers().contains(user)) {
            return "redirect:/rooms?error";
        }
        else {
            roomService.addUserToRoom(room, user);
            return "redirect:/rooms";
        }
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

    @GetMapping("/room/items")
    public String showItems(@RequestParam("roomId") Long roomId, Model model){
        RoomEntity room = roomService.getRoom(roomId);
        List<ItemEntity> roomItems = itemService.getAvailableItems(room);
        model.addAttribute("items", roomItems);
        return "room_items";
    }
    @PostMapping("/room/items/item")
    public String buyItem(@RequestParam("itemId") Long itemId,
                          @AuthenticationPrincipal UserDetails userDetails){
        UserEntity user = userService.findByUsername(userDetails.getUsername());
        ItemEntity item = itemService.getItem(itemId);
        RoomEntity room = item.getRoom();
        HeroEntity hero = heroService.getUserHero(room.getId(), user.getId());

        if (item.getHero() != null){
            return "redirect:/rooms/room/items?roomId=" + hero.getRoom().getId();
        }
        int heroGold = hero.getGold();
        int itemPrice = item.getItemData().getPrice();
        if (heroGold >= itemPrice){
            itemService.buyItem(item, hero);
            heroGold -= itemPrice;
            hero.setGold(heroGold);
            heroService.updateHero(hero.getId(), hero);

        }
        return "redirect:/rooms/room/items?roomId=" + hero.getRoom().getId();
    }
}
