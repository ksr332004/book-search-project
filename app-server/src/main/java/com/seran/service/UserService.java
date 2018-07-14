package com.seran.service;

import java.util.List;
import java.util.Optional;

import com.seran.model.History;
import com.seran.model.User;

public interface UserService {
    
	Optional<User> findUserById(Integer id);

	Optional<User> findUserByEmail(String email);

	void saveUser(User user);

	List<History> findHistorys(Integer id);
	
}