package com.demo.user_service.service;

import com.demo.user_service.dto.UserDTO;

public interface UserService {
    void createUser(UserDTO userDTO, String apikey);

}
