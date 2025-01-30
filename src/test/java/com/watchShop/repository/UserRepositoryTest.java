package com.watchShop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.watchShop.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	private User user1;
	
	@BeforeEach
	void setup() {
		this.user1 = User.builder()
				.username("user1")
				.password("password1")
				.email("email1@somewhere.com")
				.build();
	}
	
	@Test
	void testSave() {
		// Arrange
				
		// Act
		User savedUser = userRepository.save(user1);
		
		// Assert
		assertNotNull(savedUser);
		assertEquals(user1.getUsername(), savedUser.getUsername());
		assertTrue(user1.getId() == savedUser.getId());
		assertEquals(user1, savedUser);
	}
	
	@Test
	void testFindByUsername() {
		// Arrange
		userRepository.save(user1);
		
		// Act
		Optional<User> foundUser = userRepository.findByUsername(user1.getUsername());
		
		// Assert
		assertTrue(foundUser.isPresent());
		assertEquals(user1, foundUser.get());
	}
	
	@Test
	void testFindByEmail() {
		// Arrange
		userRepository.save(user1);
		
		// Act
		Optional<User> foundUser = userRepository.findByEmail(user1.getEmail());
		
		// Assert
		assertTrue(foundUser.isPresent());
		assertEquals(user1, foundUser.get());
	}

}
