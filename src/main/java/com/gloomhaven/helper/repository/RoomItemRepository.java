package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.RoomItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomItemRepository extends JpaRepository<RoomItemEntity, Long> {
}
