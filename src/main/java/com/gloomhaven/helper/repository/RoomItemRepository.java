package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.RoomItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomItemRepository extends JpaRepository<RoomItemEntity, Long> {
}
