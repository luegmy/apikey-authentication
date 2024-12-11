package com.demo.user_service.controller;

import com.demo.user_service.controller.dto.UserDTO;
import com.demo.user_service.mapper.UserMapper;
import com.demo.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO, @RequestHeader("API-Key") String apiKey) {
        return ResponseEntity.ok(userMapper.toDTO(userService.createUser(userDTO,apiKey)));
    }

}
