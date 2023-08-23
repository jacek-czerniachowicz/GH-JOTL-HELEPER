package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.HeroPerkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroPerkRepository extends JpaRepository<HeroPerkEntity, Long> {
}
