package com.demo.user_service.config;

import com.demo.user_service.mapper.UserMapper;
import org.springframework.context.annotation.Bean;

public class MapperConfig {

    @Bean
    public UserMapper userMapper() {
        return UserMapper.INSTANCE;
    }
}
