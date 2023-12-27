package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.PerkEntity;
import com.gloomhaven.helper.repository.HeroRepository;
import com.gloomhaven.helper.repository.PerkRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerkService {
    private final PerkRepository perkRepository;
    private final HeroRepository heroRepository;

    public PerkService(PerkRepository perkRepository, HeroRepository heroRepository) {
        this.perkRepository = perkRepository;
        this.heroRepository = heroRepository;
    }

    public List<PerkEntity> getValidPerks(HeroEntity hero){
        return perkRepository.findPerkEntitiesByRace(hero.getRace());
    }
    public List<PerkEntity> getValidPerks(Long heroId){
        return getValidPerks(heroRepository.findById(heroId).orElseThrow());
    }

    public PerkEntity getPerkById(Long perkId) {
        Optional<PerkEntity> optionalPerk = perkRepository.findById(perkId);
        return optionalPerk.orElse(null);
    }

    public PerkEntity update(PerkEntity perk) {
        PerkEntity existedPerk = getPerkById(perk.getId());
        existedPerk.setHeroes(perk.getHeroes());
        perkRepository.save(existedPerk);
        return existedPerk;
    }

    public PerkEntity choosePerk(PerkEntity perk, HeroEntity hero) {
        List<HeroEntity> heroes = perk.getHeroes();
        heroes.add(hero);
        perk.setHeroes(heroes);
        return perkRepository.save(perk);
    }
    @Transactional
    public PerkEntity choosePerkById(Long perkId, Long heroId) throws Exception {
        PerkEntity perk = perkRepository.findById(perkId).orElseThrow();
        HeroEntity hero = heroRepository.findById(heroId).orElseThrow();

        if (perk.getRace() != hero.getRace()){
            throw new Exception("bad id");
        }

        if (perk.getHeroes().contains(hero)){
            throw  new Exception("hero already has that perk");
        }

        if (hero.getProgressPoints() < 3){
            throw new Exception("not enough points");
        }

        hero.setProgressPoints(hero.getProgressPoints()-3);
        heroRepository.save(hero);

        return choosePerk(perk, hero);

    }

    public List<PerkEntity> getChosenPerks(Long heroId) {
        HeroEntity hero = heroRepository.findById(heroId).orElseThrow();
        return hero.getPerks();
    }
}
