package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.PerkEntity;
import com.gloomhaven.helper.repository.HeroRepository;
import com.gloomhaven.helper.repository.PerkRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerkService {
    private final PerkRepository perkRepository;
    private final HeroRepository heroRepository;

    public PerkService(PerkRepository perkRepository, HeroRepository heroRepository) {
        this.perkRepository = perkRepository;
        this.heroRepository = heroRepository;
    }

    public List<PerkEntity> getValidPerks(Long heroId) {
        HeroEntity hero = getHero(heroId);
        return perkRepository.findPerkEntitiesByRace(hero.getRace());
    }

    @Transactional
    public PerkEntity choosePerkById(Long perkId, Long heroId) throws Exception {
        PerkEntity perk = getPerk(perkId);
        HeroEntity hero = getHero(heroId);

        validatePerkChoice(perk, hero);
        updateHeroProgressPoints(hero);

        return choosePerk(perk, hero);
    }

    public List<PerkEntity> selectedPerks(Long heroId) {
        HeroEntity hero = getHero(heroId);
        return hero.getPerks();
    }

    private HeroEntity getHero(Long heroId) {
        return heroRepository.findById(heroId).orElseThrow(() -> new RuntimeException("Hero not found"));
    }

    private PerkEntity getPerk(Long perkId) {
        return perkRepository.findById(perkId).orElseThrow(() -> new RuntimeException("Perk not found"));
    }

    private void validatePerkChoice(PerkEntity perk, HeroEntity hero) throws Exception {
        if (perk.getRace() != hero.getRace()) {
            throw new Exception("Invalid perk for the hero's race");
        }

        if (perk.getHeroes().contains(hero)) {
            throw new Exception("Hero already has that perk");
        }

        if (hero.getProgressPoints() < 3) {
            throw new Exception("Not enough progress points");
        }
    }

    private void updateHeroProgressPoints(HeroEntity hero) {
        hero.setProgressPoints(hero.getProgressPoints() - 3);
        heroRepository.save(hero);
    }

    private PerkEntity choosePerk(PerkEntity perk, HeroEntity hero) {
        perk.getHeroes().add(hero);
        return perkRepository.save(perk);
    }
}
