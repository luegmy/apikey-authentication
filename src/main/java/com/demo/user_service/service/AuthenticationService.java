package com.demo.user_service.service;

public interface AuthenticationService {
    boolean authenticate(String username, String rawPassword, String apikey);
}
