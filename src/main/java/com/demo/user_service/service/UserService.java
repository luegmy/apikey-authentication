package com.demo.user_service.service;

import com.demo.user_service.controller.dto.UserDTO;
import com.demo.user_service.service.model.User;

public interface UserService {
    User createUser(UserDTO userDTO, String apikey);

}
