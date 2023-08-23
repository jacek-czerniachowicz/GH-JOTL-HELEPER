package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.HeroPerkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroPerkRepository extends JpaRepository<HeroPerkEntity, Long> {
}
