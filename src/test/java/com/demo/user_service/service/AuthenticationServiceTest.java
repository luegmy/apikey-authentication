package com.demo.user_service.service;

import com.demo.user_service.UserServiceApplicationTests;
import com.demo.user_service.exception.InvalidPasswordException;
import com.demo.user_service.exception.NotFoundUsernameException;
import com.demo.user_service.mapper.UserMapper;
import com.demo.user_service.persistence.repository.UserRepository;
import com.demo.user_service.persistence.entity.UserEntity;
import com.demo.user_service.util.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest extends UserServiceApplicationTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;
    @Mock
    private Util util;
    @InjectMocks
    private AuthenticationServiceImpl userService;

    @Test
    public void testAuthenticateUser() throws Exception {
        // Configuración
        String validApiKey = "valid-api-key";

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(convertToObject("/Authenticate.json",UserEntity.class)));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        // Acción
        boolean authenticated = userService.authenticate("testUser", "password123", validApiKey);

        // Verificación
        assertTrue(authenticated);
        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    public void testNotFoundUser() {
        // Configuración
        String validApiKey = "valid-api-key";

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Acción
        NotFoundUsernameException exception = assertThrows(NotFoundUsernameException.class, () ->
                userService.authenticate("testUser", "password123", validApiKey));

        // Verificación
        assertEquals("The user was not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    public void testInvalidPassword() throws Exception {
        // Configuración
        String validApiKey = "valid-api-key";

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(convertToObject("/Authenticate.json",UserEntity.class)));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(false);

        // Acción
        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () ->
                userService.authenticate("testUser", "password123", validApiKey));

        // Verificación
        assertEquals("Password is invalid", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(anyString());
    }
}
