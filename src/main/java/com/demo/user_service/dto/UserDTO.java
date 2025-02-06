package com.demo.user_service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    @NotBlank(message = "Please provide a username")
    private String username;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    private boolean enabled;
    private boolean accountNoExpired;
    private boolean accountNoLocked;
    private boolean credentialsNoExpired;
    private List<RoleDTO> roles;
}
