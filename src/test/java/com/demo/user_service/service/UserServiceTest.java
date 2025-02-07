package com.demo.user_service.service;


import com.demo.user_service.UserServiceApplicationTests;
import com.demo.user_service.dto.UserDTO;
import com.demo.user_service.exception.InvalidApiKeyException;
import com.demo.user_service.mapper.UserMapper;
import com.demo.user_service.persistence.entity.RolEntity;
import com.demo.user_service.persistence.repository.PermissionRepository;
import com.demo.user_service.persistence.repository.RolRepository;
import com.demo.user_service.persistence.repository.UserRepository;
import com.demo.user_service.persistence.entity.UserEntity;
import com.demo.user_service.util.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
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
    @Mock
    private Util util;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testCreateUser() throws Exception {
        // Configuración
        String validApiKey = "valid-api-key";

        doNothing().when(util).validateApiKey(anyString());
        when(userMapper.toUserEntity(any(UserDTO.class))).thenReturn(convertToObject("/User.json", UserEntity.class));
        when(rolRepository.findByRol(any())).thenReturn(Optional.of(new RolEntity()));
        when(userRepository.save(any(UserEntity.class))).thenReturn(convertToObject("/User.json", UserEntity.class));

        // Acción
        userService.createUser(convertToObject("/User.json", UserDTO.class), validApiKey);

        // Verificación
        verify(util, times(1)).validateApiKey(anyString());
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
                userService.createUser(convertToObject("/User.json", UserDTO.class), validApiKey));

        // Verificación
        assertEquals("User already exists: ", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testInvalidApiKey() {
        // Configuración
        String validApiKey = "valid-api-key";

        doThrow(new InvalidApiKeyException(HttpStatus.UNAUTHORIZED, "Invalid API Key")).when(util).validateApiKey(anyString());

        // Acción
        InvalidApiKeyException exception = assertThrows(InvalidApiKeyException.class, () ->
                userService.createUser(convertToObject("/User.json", UserDTO.class), validApiKey));

        // Verificación
        assertEquals("Invalid API Key", exception.getMessage());
        verify(util, times(1)).validateApiKey(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

}
