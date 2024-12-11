package com.demo.user_service.service.model;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String username;
    private String password;
    private String name;
    private String email;
    private String role;
}
