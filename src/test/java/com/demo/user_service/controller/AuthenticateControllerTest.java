package com.demo.user_service.controller;

import com.demo.user_service.config.SecurityConfigTest;
import com.demo.user_service.controller.dto.UserDTO;
import com.demo.user_service.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticateController.class)
@Import(SecurityConfigTest.class)
public class AuthenticateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService userService;

    @Test
    public void testAuthenticateUser() throws Exception {
        // Configuración
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");
        userDTO.setPassword("password123");

        when(userService.authenticate(anyString(), anyString(), anyString())).thenReturn(true);

        // Acción y Verificación
        mockMvc.perform(post("/api/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO))
                        .header("API-Key", "secure-api-key"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(userService, times(1)).authenticate(anyString(), anyString(), anyString());
    }
}
