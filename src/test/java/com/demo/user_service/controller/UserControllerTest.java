package com.demo.user_service.controller;

import com.demo.user_service.config.SecurityConfigTest;
import com.demo.user_service.controller.dto.UserDTO;
import com.demo.user_service.mapper.UserMapper;
import com.demo.user_service.service.UserService;
import com.demo.user_service.service.model.User;
import com.demo.user_service.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfigTest.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;

    @Test
    public void testCreateUser() throws Exception {
        // Configuración
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");
        userDTO.setPassword("password123");

        User responseDTO = new User();
        responseDTO.setUsername("testUser");

        when(userService.createUser(any(UserDTO.class), anyString())).thenReturn(responseDTO);
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        // Acción y Verificación
        mockMvc.perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO))
                        .header("API-Key", "secure-api-key"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));

        verify(userService, times(1)).createUser(any(UserDTO.class), anyString());
        verify(userMapper, times(1)).toDTO(any(User.class));
    }

}
