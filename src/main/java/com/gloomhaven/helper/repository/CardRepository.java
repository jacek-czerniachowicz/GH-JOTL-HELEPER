package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.CardEntity;
import com.gloomhaven.helper.model.entities.RacesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {
    List<CardEntity> findAllByRaceAndRequiredLevelIsLessThanEqual(RacesEnum race, int level);
}
