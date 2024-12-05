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
        validateApiKey(apiKey);
        return ResponseEntity.ok(userMapper.toDTO(userService.createUser(userDTO)));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticate(@RequestBody UserDTO userDTO, @RequestHeader("API-Key") String apiKey) {
        validateApiKey(apiKey);
        boolean authenticated = userService.authenticate(userDTO.getUsername(), userDTO.getPassword());
        return ResponseEntity.ok(authenticated);
    }

    private void validateApiKey(String apiKey) {
        if (!"secure-api-key".equals(apiKey)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid API Key");
        }
    }
}
