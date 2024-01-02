package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.InviteCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface InviteCodeRepository extends JpaRepository<InviteCodeEntity, Long> {
    Optional<InviteCodeEntity> findByRoomId(Long roomId);

    Optional<InviteCodeEntity> findByCode(String code);
}
