package com.seran.service;

import com.seran.entity.User;

import java.util.Optional;

public interface UserService {
	Optional<User> searchUserByEmail(String email);
	void saveUser(User user);
	void deleteUserById(Integer id);
}