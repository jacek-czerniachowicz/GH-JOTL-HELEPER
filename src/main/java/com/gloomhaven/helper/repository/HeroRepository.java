package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepository extends JpaRepository<HeroEntity, Long> {
    List<HeroEntity> findByRoomId(Long roomId);
    HeroEntity findByRoomIdAndUserId(Long roomId, Long userId);

    int countByRoomAndName(RoomEntity room, String heroName);
}
