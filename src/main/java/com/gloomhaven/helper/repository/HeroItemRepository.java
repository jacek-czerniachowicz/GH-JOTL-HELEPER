package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.HeroItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroItemRepository extends JpaRepository<HeroItemEntity, Long> {
}
