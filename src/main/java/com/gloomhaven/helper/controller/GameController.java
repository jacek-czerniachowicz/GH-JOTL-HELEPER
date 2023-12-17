package com.gloomhaven.helper.controller;

import com.gloomhaven.helper.model.dto.GameSummaryForm;
import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.service.HeroService;
import com.gloomhaven.helper.service.RoomService;
import com.gloomhaven.helper.service.user.IUserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game")
public class GameController {
    private final HeroService heroService;
    private final RoomService roomService;
    private final IUserService IUserService;

    public GameController(HeroService heroService, RoomService roomService, IUserService IUserService) {
        this.heroService = heroService;
        this.roomService = roomService;
        this.IUserService = IUserService;
    }

    @GetMapping("")
    public String getGamePage(@RequestParam("roomId") Long roomId, Model model){
        model.addAttribute("roomId", roomId);
        return "game_phase";
    }

    @GetMapping("/end")
    public String endGame(@AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam("roomId") Long roomId, Model model){
        UserEntity user = IUserService.findByUsername(userDetails.getUsername());
        HeroEntity hero = heroService.getUserHero(roomId, user.getId());
        model.addAttribute("formObject", new GameSummaryForm());
        model.addAttribute("hero", hero);
        return "game_summary";
    }

    @PostMapping("/end")
    public String summary(@ModelAttribute("formObject") GameSummaryForm summaryForm){
        HeroEntity hero = heroService.getHeroById(summaryForm.getHeroId());
        hero.setGold(hero.getGold() + summaryForm.getGold());
        hero.setXp(hero.getXp() + summaryForm.getXp());
        hero.setProgressPoints(hero.getProgressPoints() + summaryForm.getProgressPoint());
        heroService.updateHero(hero.getId(), hero);
        return "redirect:/rooms/room?roomId=" + hero.getRoom().getId();
    }
}
