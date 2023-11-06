package com.gloomhaven.helper.controller;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.service.HeroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HeroController {
    private HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping("/hero")
    public String getHeroDetails(Model model, @RequestParam("heroId") Long heroId){
        System.out.println(heroId);
        Optional<HeroEntity> heroOptional = heroService.getHeroById(heroId);
        if (heroOptional.isPresent()){
            HeroEntity hero = heroOptional.get();
            model.addAttribute("hero", hero);
        }

        return "hero_details";
    }
}
