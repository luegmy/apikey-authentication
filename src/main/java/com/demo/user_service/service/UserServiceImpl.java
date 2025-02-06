package com.demo.user_service.service;


import com.demo.user_service.dto.UserDTO;
import com.demo.user_service.mapper.UserMapper;
import com.demo.user_service.persistence.repository.UserRepository;
import com.demo.user_service.persistence.entity.UserEntity;
import com.demo.user_service.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Util util;
    private UserMapper userMapper;

    public void createUser(UserDTO userDTO, String apikey) {
        util.validateApiKey(apikey);
        userRepository.findByUsername(userDTO.getUsername())
                        .ifPresent(user -> {
            throw new IllegalArgumentException("User already exists: " + userDTO.getUsername());
        });
        userRepository.save(userMapper.toUserEntity(userDTO));
    }


}
