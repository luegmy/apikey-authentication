package com.demo.user_service.persistence.repository;

import com.demo.user_service.persistence.entity.PermissionEntity;
import com.demo.user_service.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PermissionRepository extends CrudRepository<PermissionEntity, Long> {
    Optional<PermissionEntity> findByPermission(String permission);
}


















