package com.demo.user_service.mapper;

import com.demo.user_service.controller.dto.UserDTO;
import com.demo.user_service.repository.entity.UserEntity;
import com.demo.user_service.service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE= Mappers.getMapper(UserMapper.class);
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "role", source = "role")
    User toUser(UserEntity userEntity);

    UserDTO toDTO(User user);
}
