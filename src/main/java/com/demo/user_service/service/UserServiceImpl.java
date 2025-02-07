package com.demo.user_service.service;


import com.demo.user_service.dto.RolDTO;
import com.demo.user_service.dto.UserDTO;
import com.demo.user_service.mapper.UserMapper;
import com.demo.user_service.persistence.entity.PermissionEntity;
import com.demo.user_service.persistence.entity.RolEntity;
import com.demo.user_service.persistence.entity.RolEnum;
import com.demo.user_service.persistence.repository.PermissionRepository;
import com.demo.user_service.persistence.repository.RolRepository;
import com.demo.user_service.persistence.repository.UserRepository;
import com.demo.user_service.persistence.entity.UserEntity;
import com.demo.user_service.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RolRepository rolRepository;
    private PermissionRepository permissionRepository;
    private PasswordEncoder passwordEncoder;
    private Util util;
    private UserMapper userMapper;

    public void createUser(UserDTO userDTO, String apikey) {
        util.validateApiKey(apikey);
        userRepository.findByUsername(userDTO.getUsername())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("User already exists: " + userDTO.getUsername());
                });

        UserEntity userEntity = userMapper.toUserEntity(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setRoles(createRolEntity(userDTO));
        userRepository.save(userEntity);
    }

    private Set<RolEntity> createRolEntity(UserDTO userDTO) {
        return userDTO.getRoles().stream().map(rolDTO -> {
            RolEnum rolEnum = validateRolEnum(rolDTO);
            return rolRepository.findByRol(rolEnum)
                    .orElseGet(() -> {
                        RolEntity rolEntity = new RolEntity();
                        rolEntity.setRol(rolEnum);
                        rolEntity.setPermissions(createPermissionEntity(rolDTO));
                        return rolRepository.save(rolEntity);
                    });

        }).collect(Collectors.toSet());
    }

    private RolEnum validateRolEnum(RolDTO rolDTO) {
        try {
            return RolEnum.valueOf(rolDTO.getRol().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Rol inválido: " + rolDTO.getRol());
        }
    }

    private Set<PermissionEntity> createPermissionEntity(RolDTO rolDTO) {
        return rolDTO.getPermissions().stream().map(permissionDTO ->
                permissionRepository.findByPermission(permissionDTO.getPermission())
                .orElseGet(() -> {
                    PermissionEntity permissionEntity = new PermissionEntity();
                    permissionEntity.setPermission(permissionEntity.getPermission());
                    return permissionRepository.save(permissionEntity);
                })).collect(Collectors.toSet());
    }
}
