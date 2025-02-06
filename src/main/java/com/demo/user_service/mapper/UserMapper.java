package com.demo.user_service.mapper;

import com.demo.user_service.dto.UserDTO;
import com.demo.user_service.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE= Mappers.getMapper(UserMapper.class);

    UserEntity toUserEntity(UserDTO userDTO);

    UserDTO toUserDTO(UserEntity userEntity);
}
