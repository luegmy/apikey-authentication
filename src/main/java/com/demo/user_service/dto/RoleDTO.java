package com.demo.user_service.dto;

import com.demo.user_service.persistence.entity.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleDTO {
    @NotBlank(message = "Please provide a role")
    private String rol;
    private List<PermissionDTO> permissions;
}
