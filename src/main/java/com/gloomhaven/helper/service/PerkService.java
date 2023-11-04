package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.PerkEntity;
import com.gloomhaven.helper.repository.PerkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerkService {
    private final PerkRepository perkRepository;

    public PerkService(PerkRepository perkRepository) {
        this.perkRepository = perkRepository;
    }

    public List<PerkEntity> getValidPerks(HeroEntity hero){
        return perkRepository.findPerkEntitiesByRace(hero.getRace());
    }
}
