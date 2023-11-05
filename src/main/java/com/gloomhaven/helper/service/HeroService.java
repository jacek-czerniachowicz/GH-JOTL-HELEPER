package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
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

    public Optional<HeroEntity> getHeroById(Long id) {
        return heroRepository.findById(id);
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
            return heroRepository.save(existingHero);
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

    public boolean isHeroNameUniqueInRoom(String heroName, RoomEntity room) {
        return heroRepository.countByRoomAndName(room, heroName) == 0;
    }
}
