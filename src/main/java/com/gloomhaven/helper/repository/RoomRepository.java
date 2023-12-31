package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    RoomEntity findRoomById(Long id);
    RoomEntity findRoomByName(String name);
}
