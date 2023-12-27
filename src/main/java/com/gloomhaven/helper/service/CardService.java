package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.CardEntity;
import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.repository.CardRepository;
import com.gloomhaven.helper.repository.HeroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    private Logger LOGGER = LoggerFactory.getLogger(CardService.class);
    private final CardRepository cardRepository;
    private final HeroRepository heroRepository;
    public CardService(CardRepository cardRepository, HeroRepository heroRepository) {
        this.cardRepository = cardRepository;
        this.heroRepository = heroRepository;
    }

    public void addCards(List<CardEntity> cards){
        cardRepository.saveAll(cards);
    }
    public List<CardEntity> getAllCards(){
        return cardRepository.findAll();
    }

    public List<CardEntity> getAvailableCards(HeroEntity hero) {

        int level = hero.getLevel();
        LOGGER.info("Current level: " + level);
        return cardRepository.findAllByRaceAndRequiredLevelIsLessThanEqual(hero.getRace(), level);
    }

    public List<CardEntity> getAvailableCards(Long heroId) {
        HeroEntity hero = heroRepository.findById(heroId).orElseThrow();

        return getAvailableCards(hero).stream().filter(card -> !card.getHeroes().contains(hero)).toList();
    }

    public CardEntity getById(Long cardId) {
        return cardRepository.findById(cardId).orElse(null);
    }
    public CardEntity chooseCard(CardEntity card, HeroEntity hero) {
        List<HeroEntity> heroes = card.getHeroes();
        heroes.add(hero);
        card.setHeroes(heroes);
        return cardRepository.save(card);
    }
    public CardEntity chooseCard(Long cardId, Long heroId) throws Exception {
        CardEntity card = cardRepository.findById(cardId).orElseThrow();
        HeroEntity hero = heroRepository.findById(heroId).orElseThrow();
        List<CardEntity> availableCards = getAvailableCards(hero);

        if (!availableCards.contains(card)) {
            throw new Exception("card not available");
        }
        if (hero.getCards().size() >= hero.getDeckSize()) {
            throw new Exception("card deck full");
        }
        return chooseCard(card, hero);
    }



    public void removeHero(CardEntity card, HeroEntity hero){
        List<HeroEntity> heroes = card.getHeroes();
        heroes.remove(hero);
        card.setHeroes(heroes);
        cardRepository.save(card);
    }

    public List<CardEntity> chosenCards(Long heroId) {
        HeroEntity hero = heroRepository.findById(heroId).orElseThrow();
        return hero.getCards();
    }

    public CardEntity unchooseCard(Long cardId, Long heroId) throws Exception {
        CardEntity card = cardRepository.findById(cardId).orElseThrow();
        HeroEntity hero = heroRepository.findById(heroId).orElseThrow();
        if (!hero.getCards().contains(card)){
            throw new Exception("card not in hero chosen cards");
        }

        removeHero(card, hero);
        return card;
    }
}
