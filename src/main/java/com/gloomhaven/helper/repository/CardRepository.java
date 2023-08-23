package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
}
