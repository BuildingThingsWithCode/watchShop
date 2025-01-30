package com.watchShop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.watchShop.model.Image;
import com.watchShop.model.Watch;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class WatchRepositoryTest {

	@Autowired
	private WatchRepository watchRepository;

	@Autowired
	private ImageRepository ImageRepository;

	@Test
	void testSave() {
		// Arrange
		Watch watch = Watch.builder()
				.name("Submariner")
				.brand("Rolex")
				.price(BigDecimal.valueOf(12500.00))
				.description("Expensive watch")
				.build();
		// Act
		Watch savedWatch = watchRepository.save(watch);

		// Assert
		assertNotNull(savedWatch);
		assertEquals(watch.getName(), savedWatch.getName());
		assertEquals(watch, savedWatch);
	}

	@Test
	void testFindByImageId() {
		// Arrange
		Image image = ImageRepository.save(Image.builder()
				.pathToImage("path/to/image.jpg")
				.build());

		Watch watch = Watch.builder()
				.name("Submariner")
				.brand("Rolex")
				.price(BigDecimal.valueOf(12500.00))
				.description("Expensive watch")
				.image(image)
				.build();
		watchRepository.save(watch);

		// Act
		Optional<Watch> foundWatch = watchRepository.findByImageId(image.getId());

		// Assert
		assertTrue(foundWatch.isPresent());
		assertEquals(watch, foundWatch.get());
	}

	@Test
	public void testFindById() {
		// Arrange
		Watch watch = watchRepository.save(Watch.builder()
				.name("Submariner")
				.brand("Rolex")
				.price(BigDecimal.valueOf(12500.00))
				.description("Expensive watch")
				.build());

		// Act
		Optional<Watch> foundWatch = watchRepository.findById(watch.getId());

		// Assert
		assertTrue(foundWatch.isPresent());
		assertEquals(watch, foundWatch.get());
	}

	@Test
	public void testDeleteById() {
		// Arrange
		Watch watch = watchRepository.save(Watch.builder()
				.name("Submariner")
				.brand("Rolex")
				.price(BigDecimal.valueOf(12500.00))
				.description("Expensive watch")
				.build());
		Optional<Watch> retrievedWatch = watchRepository.findById(watch.getId());
		assertTrue(retrievedWatch.isPresent());

		// Act
		watchRepository.deleteById(watch.getId());

		// Assert
		Optional<Watch> deletedWatch = watchRepository.findById(watch.getId());
		assertFalse(deletedWatch.isPresent());
	}
}
