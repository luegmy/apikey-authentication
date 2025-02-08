package com.demo.user_service.controller;

import com.demo.user_service.dto.UserDTO;
import com.demo.user_service.mapper.UserMapper;
import com.demo.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDTO userDTO, @RequestHeader("API-Key") String apiKey) {
        userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
