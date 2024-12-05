package com.demo.user_service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    @NotBlank(message = "Please provide a username")
    private String username;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
