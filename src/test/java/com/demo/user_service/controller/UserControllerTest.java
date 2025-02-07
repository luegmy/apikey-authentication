package com.demo.user_service.controller;

import com.demo.user_service.UserServiceApplicationTests;
import com.demo.user_service.config.CustomAccessDeniedHandler;
import com.demo.user_service.config.CustomAuthenticationEntryPoint;
import com.demo.user_service.config.SecurityConfigTest;
import com.demo.user_service.dto.UserDTO;
import com.demo.user_service.service.UserDetailsServiceImpl;
import com.demo.user_service.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfigTest.class)
public class UserControllerTest extends UserServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @MockBean
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Test
    public void givenValidCredentials_WhenCreatingUser_ThenReturnCreated() throws Exception {

        when(userDetailsService.loadUserByUsername("miguel")).thenReturn(getUserDetails());

        mockMvc.perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(convertToObject("/User.json", UserDTO.class)))
                        .header("API-Key", "secure-api-key")
                        .with(httpBasic("miguel", "1234")))
                .andExpect(status().isCreated());

        verify(userService, times(1)).createUser(any(UserDTO.class));
    }

    @Test
    public void givenInvalidCredentials_WhenCreatingUser_ThenReturnUnauthorized() throws Exception {

        when(userDetailsService.loadUserByUsername("miguel")).thenReturn(getUserDetails());

        mockMvc.perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(convertToObject("/User.json", UserDTO.class)))
                        .header("API-Key", "secure-api-key")
                        .with(httpBasic("test", "1234")))
                .andExpect(status().isUnauthorized());

        verify(userService, never()).createUser(any(UserDTO.class));
    }

    @Test
    public void givenInvalidApiKey_WhenCreatingUser_ThenReturnUnauthorized() throws Exception {

        when(userDetailsService.loadUserByUsername("miguel")).thenReturn(getUserDetails());

        mockMvc.perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(convertToObject("/User.json", UserDTO.class)))
                        .header("API-Key", "wrong-key")
                        .with(httpBasic("miguel", "1234")))
                .andExpect(status().isUnauthorized());

        verify(userService, never()).createUser(any(UserDTO.class));
    }

    @Test
    public void givenInvalidApiKeyAndNoPermission_WhenCreatingUser_ThenReturnAccessDenied() throws Exception {

        when(userDetailsService.loadUserByUsername("test")).thenReturn(getUserDetailsWithoutPermission());

        mockMvc.perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(convertToObject("/User.json", UserDTO.class)))
                        .header("API-Key", "wrong-key")
                        .with(httpBasic("test", "1234")))
                .andExpect(status().isUnauthorized());

        verify(userService, never()).createUser(any(UserDTO.class));
    }

    private UserDetails getUserDetails(){
        return User.builder()
                .username("miguel")
                .password(new BCryptPasswordEncoder().encode("1234"))
                .roles("ADMIN")
                .build();
    }

    private UserDetails getUserDetailsWithoutPermission(){
        return User.builder()
                .username("test")
                .password(new BCryptPasswordEncoder().encode("1234"))
                .roles("USER")
                .build();
    }

}
