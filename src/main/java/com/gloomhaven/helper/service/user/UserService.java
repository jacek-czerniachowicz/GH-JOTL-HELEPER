package com.gloomhaven.helper.service.user;

import com.gloomhaven.helper.model.dto.UserDTO;
import com.gloomhaven.helper.model.entities.RoleEnum;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public boolean createUser(UserDTO userDTO) {

        if(userRepository.existsByNicknameOrEmail(userDTO.getUsername(), userDTO.getEmail())){
            return false;
        }

        UserEntity user = new UserEntity(userDTO.getEmail(), userDTO.getUsername(), userDTO.getPassword());
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setRole(RoleEnum.USER);

//        RoleEntity role = roleRepository.findByName("ROLE_USER");
//        Set<RoleEntity> roles = new HashSet<>();
//        roles.add(role);
//
//        user.setRoles(roles);
//        role.getUsers().add(user);

        userRepository.save(user);
        return true;
    }
    public UserEntity createAndReturnUser(UserDTO userDTO){
        if(userRepository.existsByNicknameOrEmail(userDTO.getUsername(), userDTO.getEmail())){
            return null;
        }
        UserEntity newUser = new UserEntity();
        newUser.setNickname(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));

//        RoleEntity role = roleRepository.findByName(userDTO.getRole());
//        Set<RoleEntity> roles = new HashSet<>();
//        roles.add(role);

        newUser.setRole(RoleEnum.USER);

        return userRepository.save(newUser);

    }

    @Override
    public void setAdminRole(String username){
        UserEntity admin = findByUsername(username);
//        RoleEntity role = roleRepository.findByName("ROLE_ADMIN");
        admin.setRole(RoleEnum.ADMIN);
        userRepository.save(admin);
    }

    @Override
    public UserEntity findByUsername(String username) {

        return userRepository.findByNickname(username).orElse(null);
    }

    @Override
    public UserEntity findUser(Long userId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        return optionalUser.orElse(null);
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void removeUser(UserEntity user) {
        userRepository.delete(user);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
