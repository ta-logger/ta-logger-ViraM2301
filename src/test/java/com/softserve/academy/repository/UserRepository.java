package com.softserve.academy.repository;

import com.softserve.academy.model.User;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository {
    public static List<User> getUsersFromCsv(String resourcePath) {
        List<User> users = new ArrayList<>();
        try (InputStream is = UserRepository.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new RuntimeException("CSV file not found at: " + resourcePath);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                users = reader.lines()
                        .skip(1) // skip header
                        .map(line -> line.split(","))
                        .map(data -> User.builder()
                                .email(data[0].trim())
                                .name(data[1].trim())
                                .password(data[2].trim())
                                .build())
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
