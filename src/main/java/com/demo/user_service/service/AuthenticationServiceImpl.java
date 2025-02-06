package com.demo.user_service.service;

import com.demo.user_service.exception.InvalidPasswordException;
import com.demo.user_service.exception.NotFoundUsernameException;
import com.demo.user_service.persistence.repository.UserRepository;
import com.demo.user_service.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Util util;
    public boolean authenticate(String username, String rawPassword, String apikey) {
        util.validateApiKey(apikey);
        return userRepository.findByUsername(username)
                .map(user -> {
                    if (passwordEncoder.matches(rawPassword, user.getPassword()) && user.getUsername() != null){
                        return true;
                    } else {
                        throw new InvalidPasswordException("Password is invalid", HttpStatus.UNAUTHORIZED);
                    }
                })
                .orElseThrow(()-> new NotFoundUsernameException("The user was not found",HttpStatus.NOT_FOUND));
    }
}
