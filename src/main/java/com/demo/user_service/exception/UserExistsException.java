package com.demo.user_service.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UserExistsException extends RuntimeException {
    private static final long serialVersionUID = 6783461513543627716L;
    private final String message;
    private final HttpStatus status;

    public UserExistsException(HttpStatus status, String message) {
        this.message = message;
        this.status = status;
    }
}
