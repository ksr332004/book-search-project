package com.seran.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.seran.model.History;
import com.seran.model.User;
import com.seran.repository.HistoryRepository;
import com.seran.repository.UserRepository;
import com.seran.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private HistoryRepository historyRepository;
	
	@Override
	public Optional<User> findUserById(Integer id) {
	    return userRepository.findById(id);
	}

	@Override
	public Optional<User> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	@Transactional
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setName(user.getName());
		user.setRegistrationDate(LocalDateTime.now());
	}
	
	@Override
	public List<History> findHistorys(Integer id) {
		Optional<User> user = findUserById(id);
		return historyRepository.findTop10ByUserIdOrderByNewDateDesc(user.get().getId());
	}
	
}
