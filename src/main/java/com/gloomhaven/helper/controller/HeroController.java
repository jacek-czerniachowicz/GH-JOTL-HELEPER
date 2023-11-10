package com.gloomhaven.helper.controller;

import com.gloomhaven.helper.model.dto.PerkSelectionForm;
import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.PerkEntity;
import com.gloomhaven.helper.service.HeroService;
import com.gloomhaven.helper.service.PerkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/hero")
public class HeroController {
    private HeroService heroService;
    private PerkService perkService;

    public HeroController(HeroService heroService, PerkService perkService) {
        this.heroService = heroService;
        this.perkService = perkService;
    }

    @GetMapping("")
    public String getHeroDetails(Model model, @RequestParam("heroId") Long heroId) {
        HeroEntity hero = heroService.getHeroById(heroId);
        model.addAttribute("hero", hero);
        return "hero_details";
    }

    @GetMapping("/perks")
    public String showPerkSelectionPage(Model model, @RequestParam("heroId") Long heroId) {
        PerkSelectionForm form = new PerkSelectionForm();
        form.setHeroId(heroId);

        model.addAttribute("heroId", heroId);
        HeroEntity hero = heroService.getHeroById(heroId);
        List<PerkEntity> availablePerks = heroService.getAvailablePerks(hero);

        model.addAttribute("maxSelections", hero.getProgressPoints());
        model.addAttribute("perks", availablePerks);

        List<Long> list = hero.getPerks().stream().map(PerkEntity::getId).toList();
        form.setSelectedPerksId(list);
        list.forEach(System.out::println);

        model.addAttribute("selectedPerksId", list);
        model.addAttribute("formBackingObject", form);
        return "choose_perks";
    }

    @PostMapping("/perks")
    public String handlePerkSelection(@ModelAttribute("formBackingObject") PerkSelectionForm form) {
        Long heroId = form.getHeroId();
        HeroEntity hero = heroService.getHeroById(heroId);

        List<Long> selectedPerksId = form.getSelectedPerksId();
        for (Long perkId: selectedPerksId) {
            PerkEntity perk = perkService.getPerkById(perkId);
            if (!hero.getPerks().contains(perk)){
                perkService.addHero(perk, hero);
            }
        }
        return "redirect:/hero?heroId=" + heroId;
    }
}
