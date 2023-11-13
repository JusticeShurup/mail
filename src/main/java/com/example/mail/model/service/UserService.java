package com.example.mail.model.service;

import com.example.mail.model.domain.User;

import java.util.Optional;

public interface UserService {
    boolean findUserByUsername(String username);

    Optional<User> getUserByUsername(String username);

}
