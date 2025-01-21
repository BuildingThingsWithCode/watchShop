package com.watchShop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.watchShop.exception.DatabaseException;
import com.watchShop.exception.ImageNotFoundException;
import com.watchShop.model.Image;
import com.watchShop.repository.ImageRepository;

class ImageServiceImplTest {

	@Mock
	private ImageRepository imageRepository;

	@InjectMocks
	private ImageServiceImpl imageService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSaveImageSuccess() {
		// Arrange
		String pathToImage = "path/to/image.jpg";
		Image image = new Image();
		image.setPathToImage(pathToImage);
		when(imageRepository.save(any(Image.class))).thenReturn(image);

		// Act
		Image result = imageService.saveImage(pathToImage);

		// Assert
		assertNotNull(image);
		assertEquals(pathToImage, result.getPathToImage());
		verify(imageRepository).save(any(Image.class));
	}

	@Test
	void testSaveImageFail() {
		// Arrange
		String pathToImage = "path/to/image.jpg";
		DatabaseException exception = new DatabaseException("cause is a database error", new RuntimeException());
		when(imageRepository.save(any(Image.class))).thenThrow(exception);

		// Act & Assert
		DatabaseException result = assertThrows(DatabaseException.class, () -> imageService.saveImage(pathToImage));

		// Assert
		assertEquals("Database error occurred while saving image with path :" + pathToImage, result.getMessage());
		assertSame(exception, result.getCause());
		verify(imageRepository).save(any(Image.class));
	}

	@Test
	void testGetAllImagesSuccess() {
		// Arrange
		List<Image> expectedImageList = new ArrayList<>();
		expectedImageList.add(new Image());
		expectedImageList.add(new Image());
		when(imageRepository.findAll()).thenReturn(expectedImageList);

		// Act
		List<Image> result = imageService.getAllImages();

		// Assert
		assertNotNull(result);
		assertEquals(expectedImageList.size(), result.size());
		verify(imageRepository).findAll();
	}

	@Test
	void testGetAllImagesFail() {
		// Arrange
		DatabaseException exception = new DatabaseException("cause is a database error", new RuntimeException());
		when(imageRepository.findAll()).thenThrow(exception);

		// Act & Assert
		DatabaseException result = assertThrows(DatabaseException.class, () -> imageService.getAllImages());

		// Assert
		assertEquals("Database error occurred while retrieving all images.", result.getMessage());
		assertSame(exception, result.getCause());
		verify(imageRepository).findAll();
	}

	@Test
	void testFindByIdSuccess() {
		// Arrange
		Long id = 1L;
		Image mockImage = mock(Image.class);
		when(mockImage.getId()).thenReturn(id);
		when(imageRepository.findById(id)).thenReturn(Optional.of(mockImage));

		// Act
		Image result = imageService.findById(id);

		// Assert
		assertNotNull(result);
		assertEquals(mockImage, result);
		verify(imageRepository).findById(any(Long.class));
	}

	@Test
	void testFindByIdImageNotFound() {
		// Arrange
		Long id = 1L;
		ImageNotFoundException exception = new ImageNotFoundException("Image not found for ID: " + id);
		when(imageRepository.findById(id)).thenThrow(exception);

		// Act & Assert
		ImageNotFoundException result = assertThrows(ImageNotFoundException.class, () -> imageService.findById(id));

		// Assert
		assertEquals("Image not found for ID: " + id, result.getMessage());
		verify(imageRepository).findById(id);
	}
}
