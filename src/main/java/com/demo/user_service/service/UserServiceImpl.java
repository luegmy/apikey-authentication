package com.demo.user_service.service;


import com.demo.user_service.controller.dto.UserDTO;
import com.demo.user_service.mapper.UserMapper;
import com.demo.user_service.repository.UserRepository;
import com.demo.user_service.repository.entity.UserEntity;
import com.demo.user_service.service.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    public User createUser(UserDTO userDTO) {

        UserEntity userEntity=new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userMapper.toUser(userRepository.save(userEntity));
    }

    public boolean authenticate(String username, String rawPassword) {
        return userRepository.findByUsername(username)
                .map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }
}
