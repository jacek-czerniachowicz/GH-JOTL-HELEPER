package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}
