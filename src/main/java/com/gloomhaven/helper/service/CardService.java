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
    private final Logger LOGGER = LoggerFactory.getLogger(CardService.class);
    private final CardRepository cardRepository;
    private final HeroRepository heroRepository;
    public CardService(CardRepository cardRepository, HeroRepository heroRepository) {
        this.cardRepository = cardRepository;
        this.heroRepository = heroRepository;
    }

    public CardEntity pickCard(Long cardId, Long heroId) throws Exception {
        CardEntity card = cardRepository.findById(cardId).orElseThrow();
        HeroEntity hero = heroRepository.findById(heroId).orElseThrow();
        checkIsCardValid(card, hero);
        return addRelation(card, hero);
    }

    private void checkIsCardValid(CardEntity card, HeroEntity hero) throws Exception{
        List<CardEntity> availableCards = getAvailableCards(hero);

        if (!availableCards.contains(card)) {
            throw new Exception("card not available");
        }
        if (isHeroDeckFull(hero)) {
            throw new Exception("card deck full");
        }
    }
    private boolean isHeroDeckFull(HeroEntity hero){
        return hero.getCards().size() >= hero.getDeckSize();
    }
    private CardEntity addRelation(CardEntity card, HeroEntity hero) {
        List<HeroEntity> heroes = card.getHeroes();
        heroes.add(hero);
        card.setHeroes(heroes);
        return cardRepository.save(card);
    }

    public List<CardEntity> getSelectedCards(Long heroId) {
        HeroEntity hero = heroRepository.findById(heroId).orElseThrow();
        return hero.getCards();
    }

    public CardEntity deselectCard(Long cardId, Long heroId) throws Exception {
        CardEntity card = cardRepository.findById(cardId).orElseThrow();
        HeroEntity hero = heroRepository.findById(heroId).orElseThrow();
        checkIsCardInDeck(card, hero);
        removeRelation(card, hero);
        return card;
    }

    private void checkIsCardInDeck(CardEntity card, HeroEntity hero) throws Exception {
        if (!hero.getCards().contains(card)){
            throw new Exception("card not in hero deck");
        }
    }

    private void removeRelation(CardEntity card, HeroEntity hero){
        List<HeroEntity> heroes = card.getHeroes();
        heroes.remove(hero);
        card.setHeroes(heroes);
        cardRepository.save(card);
    }

    public void addCards(List<CardEntity> cards){
        cardRepository.saveAll(cards);
    }
    public List<CardEntity> getAllCards(){
        return cardRepository.findAll();
    }
    public List<CardEntity> getAvailableCards(HeroEntity hero) {
        return cardRepository.findAllByRaceAndRequiredLevelIsLessThanEqual(hero.getRace(), hero.getLevel());
    }
    public List<CardEntity> getAvailableCards(Long heroId) {
        HeroEntity hero = heroRepository.findById(heroId).orElseThrow();

        return getAvailableCards(hero).stream().filter(card -> !card.getHeroes().contains(hero)).toList();
    }
    public CardEntity getById(Long cardId) {
        return cardRepository.findById(cardId).orElse(null);
    }
}
