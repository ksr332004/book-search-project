package com.seran.repository;

import com.seran.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void repositorySavesUser() {
		User user = new User();
		user.setName("John");
		user.setEmail("test@test.com");
		user.setPassword("test123!@#");
		user.setRole("USER");
		User result = userRepository.save(user);

		assertEquals(result.getName(), "John");
		assertEquals(result.getEmail(), "test@test.com");
	}
}
