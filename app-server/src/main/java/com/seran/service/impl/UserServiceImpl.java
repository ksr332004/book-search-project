package com.seran.service.impl;

import com.seran.entity.User;
import com.seran.repository.UserRepository;
import com.seran.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public Optional<User> searchUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	@Transactional
	public void saveUser(User registrationUser) {
	    try {
            User user = new User();
            user.setEmail(registrationUser.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(registrationUser.getPassword()));
            user.setName(registrationUser.getName());
            user.setRole("ROLE_USER");
            user.setRegistrationDate(LocalDateTime.now());
            userRepository.save(user);
        } catch (Exception e) {
            throw new BadCredentialsException("insert error.");
        }
	}
	
	@Override
	@Transactional
	public void deleteUserById(Integer id) {
	    userRepository.deleteById(id);
	}

}
