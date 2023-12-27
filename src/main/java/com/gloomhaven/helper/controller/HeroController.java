package com.gloomhaven.helper.controller;

import com.gloomhaven.helper.model.dto.CardSelectionForm;
import com.gloomhaven.helper.model.dto.PerkSelectionForm;
import com.gloomhaven.helper.model.entities.CardEntity;
import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.PerkEntity;
import com.gloomhaven.helper.service.CardService;
import com.gloomhaven.helper.service.HeroService;
import com.gloomhaven.helper.service.PerkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hero")
public class HeroController {
    private HeroService heroService;
    private PerkService perkService;
    private CardService cardService;

    public HeroController(HeroService heroService, PerkService perkService, CardService cardService) {
        this.heroService = heroService;
        this.perkService = perkService;
        this.cardService = cardService;
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
        model.addAttribute("perks", availablePerks);

        List<Long> list = hero.getPerks().stream().map(PerkEntity::getId).toList();
        form.setSelectedPerksId(list);

        int perkPoints = hero.getProgressPoints()/3;
        model.addAttribute("selectedPerksId", list);
        model.addAttribute("maxSelections", perkPoints);
        model.addAttribute("formObject", form);
        return "choose_perks";
    }

    @PostMapping("/perks")
    public String handlePerkSelection(@ModelAttribute("formObject") PerkSelectionForm form) {
        Long heroId = form.getHeroId();
        HeroEntity hero = heroService.getHeroById(heroId);

        List<Long> selectedPerksId = form.getSelectedPerksId();
        for (Long perkId: selectedPerksId) {
            PerkEntity perk = perkService.getPerkById(perkId);
            if (!hero.getPerks().contains(perk)){
                perkService.choosePerk(perk, hero);
            }
        }
        return "redirect:/hero?heroId=" + heroId;
    }

    @GetMapping("/cards")
    public String showCardSelectionPage(Model model, @RequestParam("heroId") Long heroId){
        CardSelectionForm form = new CardSelectionForm();
        form.setHeroId(heroId);
        model.addAttribute("heroId", heroId);

        HeroEntity hero = heroService.getHeroById(heroId);
        List<CardEntity> availableCards = cardService.getAvailableCards(hero);
        model.addAttribute("cards", availableCards);

        List<Long> chosenCards = hero.getCards().stream().map(CardEntity::getId).toList();
        form.setSelectedCardsId(chosenCards);

        model.addAttribute("selectedCardsId", chosenCards);

        int maxCardsOnHand = 0;
        switch (hero.getRace()){
            case DEMOLITIONIST -> maxCardsOnHand = 9;
            case HATCHET, REDGUARD -> maxCardsOnHand = 10;
            case VOIDWARDEN -> maxCardsOnHand = 11;
        }
        model.addAttribute("maxSelections", maxCardsOnHand);
        model.addAttribute("formObject", form);

        return "choose_cards";
    }

    @PostMapping("/cards")
    public String handleCardSelection(@ModelAttribute("formObject") CardSelectionForm form){
        Long heroId = form.getHeroId();
        HeroEntity hero = heroService.getHeroById(heroId);
        List<CardEntity> heroCards = hero.getCards();
        List<Long> chosenCardsId = form.getSelectedCardsId();
        List<CardEntity> chosenCards = chosenCardsId.stream().map(id -> cardService.getById(id)).toList();

        for (CardEntity card: heroCards){
            if (!chosenCards.contains(card)){
                cardService.removeHero(card, hero);
            }
        }
        for (CardEntity card: chosenCards){
            if (!heroCards.contains(card)){
                cardService.chooseCard(card, hero);
            }
        }
        return "redirect:/hero?heroId=" + heroId;
    }
}
