package com.seran.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.seran.model.User;
import com.seran.repository.UserRepository;
import com.seran.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	public Optional<User> searchUserById(Integer id) {
	    return userRepository.findById(id);
	}

	@Override
	public Optional<User> searchUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	@Transactional
	public void saveUser(User registUser) {
	    try {
            User user = new User();
            user.setEmail(registUser.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(registUser.getPassword()));
            user.setName(registUser.getName());
            user.setRole("ROLE_USER");
            user.setRegistrationDate(LocalDateTime.now());
            userRepository.save(user);
        } catch (Exception e) {
            throw new BadCredentialsException("Bad request.");
        }
	}
	
	@Override
	@Transactional
	public void deleteUserByEmail(String email) {
	    userRepository.deleteByEmail(email);
	}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }
	
}
