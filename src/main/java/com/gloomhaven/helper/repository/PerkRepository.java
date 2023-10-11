package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.PerkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerkRepository extends JpaRepository<PerkEntity, Long> {
}
