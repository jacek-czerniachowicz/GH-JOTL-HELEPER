package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    ItemEntity findAllById(long id);
    List<ItemEntity> findAllByRoomAndHeroIsNull(RoomEntity room);
    List<ItemEntity> findAllByRoomIdAndHeroIsNull(long roomId);

//    List<ItemEntity> findAllByHeroIdAndRoomIdAndEquippedIsTrue(long roomId, long heroId);
}
