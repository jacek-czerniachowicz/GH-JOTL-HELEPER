package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.PerkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerkRepository extends JpaRepository<PerkEntity, Long> {
}
