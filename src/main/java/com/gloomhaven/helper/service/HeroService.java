package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.PerkEntity;
import com.gloomhaven.helper.repository.HeroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HeroService {
    private final HeroRepository heroRepository;

    private final PerkService perkService;

    public HeroService(HeroRepository heroRepository, PerkService perkService) {
        this.heroRepository = heroRepository;
        this.perkService = perkService;
    }

    public List<HeroEntity> getAllHeroes() {
        return heroRepository.findAll();
    }

    public HeroEntity getHeroById(Long id) {
        Optional<HeroEntity> optionalHero = heroRepository.findById(id);
        return optionalHero.orElse(null);
    }
    public List<HeroEntity> getHeroesByRoomId(Long roomId) {
        return heroRepository.findByRoomId(roomId);
    }

    @Transactional
    public HeroEntity createHero(HeroEntity hero) {
        return heroRepository.save(hero);
    }

    @Transactional
    public HeroEntity updateHero(Long id, HeroEntity updatedHero) {
        Optional<HeroEntity> existingHeroOptional = heroRepository.findById(id);

        if (existingHeroOptional.isPresent()) {
            HeroEntity existingHero = existingHeroOptional.get();
            existingHero.setGold(updatedHero.getGold());
            existingHero.setProgressPoints(updatedHero.getProgressPoints());
            existingHero.setXp(updatedHero.getXp());
            existingHero.setItems(updatedHero.getItems());
            existingHero.setPerks(updatedHero.getPerks());
            existingHero.setCards(updatedHero.getCards());
            heroRepository.save(existingHero);
            return existingHero;
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteHero(Long id) {
        heroRepository.deleteById(id);
    }

    public HeroEntity getUserHero(Long roomId, Long userId){
        return heroRepository.findByRoomIdAndUserId(roomId, userId);
    }

    public List<PerkEntity> getAvailablePerks(HeroEntity hero){
        return perkService.getValidPerks(hero);
    }
}
