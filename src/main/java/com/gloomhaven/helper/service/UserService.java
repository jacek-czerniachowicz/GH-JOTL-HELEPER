package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.dto.UserDTO;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;

import java.util.List;

public interface UserService {
    boolean createUser(UserDTO userDTO);
    void setAdminRole(String username);
    UserEntity findByUsername(String username);
    List<UserEntity> getUsers();
}
