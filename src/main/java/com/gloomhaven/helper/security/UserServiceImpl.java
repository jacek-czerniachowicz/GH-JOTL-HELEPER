package com.gloomhaven.helper.security;

import com.gloomhaven.helper.model.entities.RoleEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.repository.RoleRepository;
import com.gloomhaven.helper.repository.UserRepository;
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
    public UserEntity createUser(UserEntity user) {
        user.setPassword(encoder.encode(user.getPassword()));

        RoleEntity role = roleRepository.findByName("ROLE_USER");
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(role);

        user.setRoles(roles);
        role.getUsers().add(user);

        return userRepository.save(user);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }
}
