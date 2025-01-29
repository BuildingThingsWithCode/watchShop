package com.watchShop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.watchShop.model.Image;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ImageRepositoryTest {
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Test
	void testSave() {
		// Arrange
		Image image = Image.builder()
				.id(1L)
				.pathToImage("path/to/image.jpg")
				.build();
		//Act
		Image savedImage = imageRepository.save(image);
		
		//Assert
		assertNotNull(savedImage);
		assertEquals(image, savedImage);
	}
}
