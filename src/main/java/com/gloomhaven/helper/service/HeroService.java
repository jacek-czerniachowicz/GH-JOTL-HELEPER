package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.dto.rest.UpdateHeroRequest;
import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.repository.HeroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HeroService {
    private final HeroRepository heroRepository;

    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
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
    public HeroEntity addHero(HeroEntity hero) {
        return heroRepository.save(hero);
    }

    public HeroEntity updateHero(Long id, UpdateHeroRequest request){
        HeroEntity hero = heroRepository.findById(id).orElseThrow();

        hero.setGold(request.getGold() != null ? request.getGold() : hero.getGold());
        hero.setXp(request.getXp() != null ? request.getXp() : hero.getXp());
        hero.setProgressPoints(request.getProgressPoint() != null ? request.getProgressPoint() : hero.getProgressPoints());

        return heroRepository.save(hero);
    }

    public HeroEntity getHeroByUserId(Long roomId, Long userId){
        return heroRepository.findByRoomIdAndUserId(roomId, userId);
    }
    public HeroEntity getUserHero(Long roomId, String userEmail){
        return heroRepository.findByRoomIdAndUserEmail(roomId, userEmail).orElseThrow();
    }
    @Transactional
    public void deleteHero(Long id) {
        heroRepository.deleteById(id);
    }
}
