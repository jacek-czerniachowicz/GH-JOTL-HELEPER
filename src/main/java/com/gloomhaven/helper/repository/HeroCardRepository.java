package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.HeroCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroCardRepository extends JpaRepository<HeroCardEntity, Long> {
}
