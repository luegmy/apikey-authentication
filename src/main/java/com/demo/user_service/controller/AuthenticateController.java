package com.demo.user_service.controller;

import com.demo.user_service.controller.dto.UserDTO;
import com.demo.user_service.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AuthenticateController {

    private AuthenticationService authenticationService;
    @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticate(@RequestBody UserDTO userDTO, @RequestHeader("API-Key") String apiKey) {
        boolean authenticated = authenticationService.authenticate(userDTO.getUsername(), userDTO.getPassword(), apiKey);
        return ResponseEntity.ok(authenticated);
    }
}
