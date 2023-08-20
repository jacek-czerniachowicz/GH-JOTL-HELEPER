package com.gloomhaven.helper.model.repositories;

import com.gloomhaven.helper.model.entities.HeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<HeroEntity, Long> {
}
