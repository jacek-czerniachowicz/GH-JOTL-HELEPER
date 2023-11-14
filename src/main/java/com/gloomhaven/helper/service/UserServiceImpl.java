package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.dto.UserDTO;
import com.gloomhaven.helper.model.entities.RoleEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.repository.RoleRepository;
import com.gloomhaven.helper.repository.RoomRepository;
import com.gloomhaven.helper.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public boolean createUser(UserDTO userDTO) {

        if(userRepository.existsByUsernameOrEmail(userDTO.getUsername(), userDTO.getEmail())){
            return false;
        }

        UserEntity user = new UserEntity(userDTO.getEmail(), userDTO.getUsername(), userDTO.getPassword());
        user.setPassword(encoder.encode(user.getPassword()));

        RoleEntity role = roleRepository.findByName("ROLE_USER");
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(role);

        user.setRoles(roles);
        role.getUsers().add(user);

        userRepository.save(user);
        return true;
    }

    @Override
    public void setAdminRole(String username){
        UserEntity admin = findByUsername(username);
        RoleEntity role = roleRepository.findByName("ROLE_ADMIN");
        admin.getRoles().add(role);
        userRepository.save(admin);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void removeUser(UserEntity user) {
        userRepository.delete(user);
    }
}
