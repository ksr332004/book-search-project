package com.seran.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.seran.entity.User;

public interface UserService extends UserDetailsService {
    
    Optional<User> searchUserById(Integer id);
	Optional<User> searchUserByEmail(String email);
	void saveUser(User user);
	void deleteUserByEmail(String email);

}