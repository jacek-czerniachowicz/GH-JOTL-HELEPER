package com.gloomhaven.helper.service.user;

import com.gloomhaven.helper.model.dto.UserDTO;
import com.gloomhaven.helper.model.entities.UserEntity;

import java.util.List;

public interface IUserService {
    boolean createUser(UserDTO userDTO);
    void setAdminRole(String username);
    UserEntity findByUsername(String username);
    UserEntity findUser(Long userId);
    List<UserEntity> getUsers();
    void removeUser(UserEntity user);
}
