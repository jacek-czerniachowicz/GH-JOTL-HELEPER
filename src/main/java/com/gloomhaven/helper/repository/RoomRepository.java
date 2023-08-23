package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}
