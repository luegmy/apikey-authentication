package com.demo.user_service.persistence.repository;

import com.demo.user_service.persistence.entity.RolEntity;
import com.demo.user_service.persistence.entity.RolEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RolRepository extends CrudRepository<RolEntity,Long> {
    Optional<RolEntity> findByRol(RolEnum rolEnum);
}
