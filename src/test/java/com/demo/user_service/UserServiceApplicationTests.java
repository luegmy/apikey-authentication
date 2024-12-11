package com.demo.user_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@SpringBootTest
public abstract class UserServiceApplicationTests {

    private String getJsonFromPath(String pathJson) throws Exception {
        return new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(getClass()
                .getResource(pathJson)).toURI())));
    }

    public  <T> T convertToObject(String pathJson, Class<T> _class) throws Exception{
        String jsonRequest =getJsonFromPath(pathJson);
        ObjectMapper mapper=new ObjectMapper();
        return mapper.readValue(jsonRequest,_class);
    }

}
