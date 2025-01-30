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

    User toUser(UserEntity userEntity);

    UserDTO toDTO(User user);
}
