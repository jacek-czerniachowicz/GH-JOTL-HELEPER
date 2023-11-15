package com.gloomhaven.helper.repository;

import com.gloomhaven.helper.model.entities.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    @NotNull Optional<UserEntity> findById(@NotNull Long id);
    boolean existsByUsernameOrEmail(String username, String email);
}
