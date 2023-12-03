package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.CardEntity;
import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.PerkEntity;
import com.gloomhaven.helper.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void addCards(List<CardEntity> cards){
        cardRepository.saveAll(cards);
    }
    public List<CardEntity> getAllCards(){
        return cardRepository.findAll();
    }

    public List<CardEntity> getAvailableCards(HeroEntity hero) {

        int level = 1; //FIXME: Change it when calculation xp to level will be implemented
        return cardRepository.findAllByRaceAndRequiredLevelIsLessThanEqual(hero.getRace(), level);
    }

    public CardEntity getById(Long cardId) {
        return cardRepository.findById(cardId).orElse(null);
    }

    public void addHero(CardEntity card, HeroEntity hero) {
        List<HeroEntity> heroes = card.getHeroes();
        heroes.add(hero);
        card.setHeroes(heroes);
        cardRepository.save(card);
    }
    public void removeHero(CardEntity card, HeroEntity hero){
        List<HeroEntity> heroes = card.getHeroes();
        heroes.remove(hero);
        card.setHeroes(heroes);
        cardRepository.save(card);
    }
}
