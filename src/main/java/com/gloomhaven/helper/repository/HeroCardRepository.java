package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.HeroCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroCardRepository extends JpaRepository<HeroCardEntity, Long> {
}
