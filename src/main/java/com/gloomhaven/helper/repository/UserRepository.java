package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    void updateByUsername(UserEntity user, String username);
}
