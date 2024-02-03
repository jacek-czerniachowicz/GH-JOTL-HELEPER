package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.CardEntity;
import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.RacesEnum;
import com.gloomhaven.helper.repository.CardRepository;
import com.gloomhaven.helper.repository.HeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static com.gloomhaven.helper.model.entities.RacesEnum.DEMOLITIONIST;
import static com.gloomhaven.helper.model.entities.RacesEnum.VOIDWARDEN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {
    private CardRepository cardRepository;
    private HeroRepository heroRepository;
    private CardService cardService;

    private List<CardEntity> cards;
    private CardEntity card1;
    private CardEntity card2;

    @BeforeEach
    public void setUp() {
        cardRepository = Mockito.mock(CardRepository.class);
        heroRepository = Mockito.mock(HeroRepository.class);
        cardService = new CardService(cardRepository, heroRepository);

        card1 = new CardEntity("card1", 8, VOIDWARDEN);
        card2 = new CardEntity("card2", 0, VOIDWARDEN);
        cards = Arrays.asList(card1, card2);

    }

    @Test
    void testAddCards() {
        cardService.addCards(cards);
        verify(cardRepository, times(1)).saveAll(cards);
    }

    @Test
    void testGetAllCards() {
        when(cardRepository.findAll()).thenReturn(cards);
        List<CardEntity> result = cardService.getAllCards();
        assertEquals(cards, result);
    }

    @Test
    void testGetAvailableCards() {
        HeroEntity hero = new HeroEntity();
        hero.setRace(VOIDWARDEN);
        hero.setXp(0);

        when(cardRepository.findAllByRaceAndRequiredLevelIsLessThanEqual(VOIDWARDEN, 1))
                .thenReturn(Arrays.asList(card2));

        List<CardEntity> result = cardService.getAvailableCards(hero);

        assertEquals(1, result.size());
        assertEquals(card2, result.get(0));
    }

    @Test
    void getById() {
        when(cardRepository.findById(1L)).thenReturn(Optional.ofNullable(card1));
        CardEntity result = cardService.getById(1L);
        assertEquals(result, card1);
    }

    @Test
    public void testSaveChooseCard() {
        CardEntity card = new CardEntity();
        HeroEntity hero = new HeroEntity();
        List<HeroEntity> heroes = new ArrayList<>();
        card.setHeroes(heroes);

        when(cardRepository.save(any(CardEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        CardEntity result = cardService.chooseCard(card, hero);

        assertEquals(card, result);
        assertTrue(result.getHeroes().contains(hero));
    }
    @Test
    void testChooseCardNotAvailable(){
        Long cardId = 1L;
        Long heroId = 1L;
        HeroEntity hero = new HeroEntity();
        hero.setRace(VOIDWARDEN);
        hero.setCards(Arrays.asList(card2));

        when(cardRepository.findById(1L)).thenReturn(Optional.ofNullable(card1));
        when(heroRepository.findById(1L)).thenReturn(Optional.of(hero));
        when(cardService.getAvailableCards(hero)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(Exception.class, () -> {
            cardService.chooseCard(cardId, heroId);
        });

        assertEquals("card not available", exception.getMessage());
    }
    @Test
    void testChooseCardDeckFull(){
        Long cardId = 1L;
        Long heroId = 1L;
        HeroEntity hero = new HeroEntity();
        hero.setRace(DEMOLITIONIST);
        List<CardEntity> heroCards = new ArrayList<>();
        for (int i = 0; i < hero.getDeckSize(); i++) {
            heroCards.add(new CardEntity());
        }
        hero.setCards(heroCards);

        when(cardRepository.findById(1L)).thenReturn(Optional.ofNullable(card1));
        when(heroRepository.findById(1L)).thenReturn(Optional.of(hero));
        when(cardService.getAvailableCards(hero)).thenReturn(Arrays.asList(card1));

        Exception exception = assertThrows(Exception.class, () -> {
            cardService.chooseCard(cardId, heroId);
        });

        assertEquals("card deck full", exception.getMessage());
    }

    @Test
    public void testRemoveHero() {
        CardEntity card = new CardEntity();
        HeroEntity hero = new HeroEntity();
        List<HeroEntity> heroes = new ArrayList<>(Arrays.asList(hero));
        card.setHeroes(heroes);

        when(cardRepository.save(any(CardEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        cardService.removeHero(card, hero);

        assertFalse(card.getHeroes().contains(hero));
    }

    @Test
    public void testChosenCards() {
        Long heroId = 1L;
        HeroEntity hero = new HeroEntity();
        CardEntity card = new CardEntity();
        List<CardEntity> cards = new ArrayList<>(Arrays.asList(card));
        hero.setCards(cards);

        when(heroRepository.findById(heroId)).thenReturn(Optional.of(hero));

        List<CardEntity> result = cardService.chosenCards(heroId);

        assertEquals(cards, result);
    }

    @Test
    public void testUnchooseCard_CardNotChosen() {
        Long cardId = 1L;
        Long heroId = 1L;

        CardEntity card = new CardEntity();
        HeroEntity hero = new HeroEntity();
        hero.setCards(new ArrayList<>());

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
        when(heroRepository.findById(heroId)).thenReturn(Optional.of(hero));

        Exception exception = assertThrows(Exception.class, () -> {
            cardService.unchooseCard(cardId, heroId);
        });

        assertEquals("card not in hero chosen cards", exception.getMessage());
    }

}