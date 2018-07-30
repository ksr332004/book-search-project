package com.seran.service;

import com.seran.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> searchUserByEmail(String email);
    Optional<User> searchUserById(Integer id);
    void saveUser(User user);
    void updateUser(Integer id, User updateUser);
    void deleteUserById(Integer id);
}