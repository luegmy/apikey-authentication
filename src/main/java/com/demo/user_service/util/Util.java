package com.demo.user_service.util;

import com.demo.user_service.exceptions.InvalidApiKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Util {

    private static final String API_KEY = "secure-api-key";

    public void validateApiKey(String apiKey) {
        if (!API_KEY.equals(apiKey)) {
            throw new InvalidApiKeyException(HttpStatus.UNAUTHORIZED, "Invalid API Key");
        }
    }
}
