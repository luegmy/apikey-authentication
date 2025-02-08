package com.demo.user_service.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(name = "is_Enabled")
    private boolean enabled;
    @Column(name = "account_No_Expired")
    private boolean accountNoExpired;
    @Column(name = "account_No_Locked")
    private boolean accountNoLocked;
    @Column(name = "credentials_No_Expired")
    private boolean credentialsNoExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolEntity> roles = new HashSet<>();
}
