package com.watchShop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.watchShop.model.Role;
import com.watchShop.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository roleRepository;

	@Test
	void testSave() {
		// Arrange
		User user1 = User.builder().username("user1").password("password1").email("email1@somewhere.com").build();
		User user2 = User.builder().username("user2").password("password2").email("email2@somewhere.com").build();
		Role role = Role.builder().name("USER").users(Arrays.asList(user1, user2)).build();
		List<Role> roleCollection = new ArrayList<>();
		roleCollection.add(role);
		user1.setRoles(roleCollection);
		user2.setRoles(roleCollection);
		
		//Act
		Role savedRole = roleRepository.save(role);

		//Assert
		assertNotNull(savedRole);
		assertNotNull(savedRole.getId());
		assertEquals(savedRole.getName(), "USER");
		assertEquals(2, savedRole.getUsers().size());
		assertTrue(savedRole.getUsers().contains(user1));
		assertTrue(savedRole.getUsers().contains(user2));
		assertTrue(user1.getRoles().contains(savedRole));
		assertTrue(user2.getRoles().contains(savedRole)); 
	}

	//	@Test
	//	public void testFindByName() {
	//		// Arrange
	//		User user1 = User.builder().build();
	//		User user2 = User.builder().build();
	//		List<User> userList = new ArrayList<>();
	//		userList.add(user1);
	//		userList.add(user2);
	//		Role role = Role.builder().name("USER").users(userList).build();
	//		
	//		// Act
	//		
	//		// Assert
	//	}
}
