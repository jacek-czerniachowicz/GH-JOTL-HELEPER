package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.PerkEntity;
import com.gloomhaven.helper.repository.PerkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerkService {
    private final PerkRepository perkRepository;

    public PerkService(PerkRepository perkRepository) {
        this.perkRepository = perkRepository;
    }

    public List<PerkEntity> getValidPerks(HeroEntity hero){
        return perkRepository.findPerkEntitiesByRace(hero.getRace());
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

    public void addHero(PerkEntity perk, HeroEntity hero) {
        List<HeroEntity> heroes = perk.getHeroes();
        heroes.add(hero);
        perk.setHeroes(heroes);
        perkRepository.save(perk);
    }

}
