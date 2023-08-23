package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.HeroItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroItemRepository extends JpaRepository<HeroItemEntity, Long> {
}
