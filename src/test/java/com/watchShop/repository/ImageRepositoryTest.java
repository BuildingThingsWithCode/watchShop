package com.watchShop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

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
				.pathToImage("path/to/image.jpg")
				.build();
		//Act
		Image savedImage = imageRepository.save(image);

		//Assert
		assertNotNull(savedImage);
		assertNotNull(savedImage.getId());
		assertEquals(image, savedImage);
	}

	@Test
	void testFindAll() {
		// Arrange
		Image image1 = Image.builder().pathToImage("path/to/image1.jpg").build();
		Image image2 = Image.builder().pathToImage("path/to/image2.jpg").build();
		imageRepository.save(image1);
		imageRepository.save(image2);

		// Act
		List<Image> images = imageRepository.findAll();

		// Assert
		assertNotNull(images);
		assertEquals(2, images.size());
		assertTrue(images.contains(image1));
		assertTrue(images.contains(image2));
	}
	
	@Test
	void testFindById() {
		// Arrange
		Image image1 = imageRepository.save(Image.builder().pathToImage("path/to/image1.jpg").build());
		Image image2 = imageRepository.save(Image.builder().pathToImage("path/to/image2.jpg").build());
		
		// Act
		Optional<Image> savedImage1 = imageRepository.findById(image1.getId());
		Optional<Image> savedImage2 = imageRepository.findById(image2.getId());
		
		// Assert
		assertTrue(savedImage1.isPresent());
		assertTrue(savedImage2.isPresent());
		assertEquals(savedImage1.get().getPathToImage(), "path/to/image1.jpg");
		assertEquals(savedImage2.get().getPathToImage(), "path/to/image2.jpg");
	}
}
