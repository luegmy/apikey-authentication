package com.demo.user_service.service;

import com.demo.user_service.controller.dto.UserDTO;
import com.demo.user_service.repository.UserRepository;
import com.demo.user_service.repository.entity.UserEntity;
import com.demo.user_service.service.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testCreateUser() throws Exception {
        String userJson = "{\"username\":\"testUser\",\"password\":\"password123\"}";

        mockMvc.perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .header("API-Key", "secure-api-key"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setPassword(new BCryptPasswordEncoder().encode("password123"));
        userRepository.save(user);

        String authJson = "{\"username\":\"testUser\",\"password\":\"password123\"}";

        mockMvc.perform(post("/api/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authJson)
                        .header("API-Key", "secure-api-key"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

}
