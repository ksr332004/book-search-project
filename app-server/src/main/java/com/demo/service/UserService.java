package com.demo.service;

import java.util.Optional;

import com.demo.model.User;

public interface UserService {
	Optional<User> findUserById(Integer id);

	Optional<User> findUserByEmail(String email);

	void saveUser(User user);
}
