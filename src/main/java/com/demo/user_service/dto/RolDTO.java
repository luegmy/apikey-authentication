package com.demo.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RolDTO {
    @NotBlank(message = "Please provide a role")
    private String rol;
    private List<PermissionDTO> permissions;
}
