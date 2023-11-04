package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.PerkEntity;
import com.gloomhaven.helper.model.entities.RacesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerkRepository extends JpaRepository<PerkEntity, Long> {
    List<PerkEntity> findPerkEntitiesByRace(RacesEnum race);
}
