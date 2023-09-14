package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity createUser(UserEntity user);
    UserEntity findByUsername(String username);
    List<UserEntity> getUsers();
}
