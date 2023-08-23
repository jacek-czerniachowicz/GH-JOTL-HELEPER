package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {
}
