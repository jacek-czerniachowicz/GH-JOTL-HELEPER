package com.gloomhaven.helper.model.repositories;

import com.gloomhaven.helper.model.entities.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
}
