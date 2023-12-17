package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByNickname(String nickname);
    Optional<UserEntity> findByEmail(String email);
    @NotNull Optional<UserEntity> findById(@NotNull Long id);
    boolean existsByNicknameOrEmail(String username, String email);
}
