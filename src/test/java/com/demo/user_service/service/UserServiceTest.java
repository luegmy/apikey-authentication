package com.demo.user_service.service;


import com.demo.user_service.UserServiceApplicationTests;
import com.demo.user_service.dto.UserDTO;
import com.demo.user_service.mapper.UserMapper;
import com.demo.user_service.persistence.entity.RolEntity;
import com.demo.user_service.persistence.repository.PermissionRepository;
import com.demo.user_service.persistence.repository.RolRepository;
import com.demo.user_service.persistence.repository.UserRepository;
import com.demo.user_service.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends UserServiceApplicationTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RolRepository rolRepository;
    @Mock
    private PermissionRepository permissionRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testCreateUser() throws Exception {

        when(userMapper.toUserEntity(any(UserDTO.class))).thenReturn(convertToObject("/User.json", UserEntity.class));
        when(rolRepository.findByRol(any())).thenReturn(Optional.of(new RolEntity()));
        when(userRepository.save(any(UserEntity.class))).thenReturn(convertToObject("/User.json", UserEntity.class));

        // Acción
        userService.createUser(convertToObject("/User.json", UserDTO.class));

        // Verificación
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userMapper, times(1)).toUserEntity(any(UserDTO.class));
    }

    @Test
    public void testUsernameExists() {
        // Configuración
        String validApiKey = "valid-api-key";

        doThrow(new IllegalArgumentException("User already exists: ")).when(userRepository).findByUsername(anyString());

        // Acción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                userService.createUser(convertToObject("/User.json", UserDTO.class)));

        // Verificación
        assertEquals("User already exists: ", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }


}
