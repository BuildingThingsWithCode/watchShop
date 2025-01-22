package com.watchShop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.watchShop.dto.WatchDto;
import com.watchShop.dto.WatchMapper;
import com.watchShop.exception.DatabaseException;
import com.watchShop.exception.WatchNotFoundException;
import com.watchShop.model.Image;
import com.watchShop.model.Watch;
import com.watchShop.repository.WatchRepository;

class WatchServiceImplTest {

    @Mock
    private WatchRepository watchRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private WatchMapper watchMapper;

    @InjectMocks
    private WatchServiceImpl watchService;

    private Watch watch;
    private WatchDto watchDto;
    private Image image;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        image = new Image();
        image.setId(1L);
        image.setPathToImage("/path/to/image.jpg");
        watch = new Watch(1L, "Submariner", "Rolex", BigDecimal.valueOf(12600.00), "40mm, steel, 1560cal", image);
        watchDto = new WatchDto(1L, "Submariner", "Rolex", BigDecimal.valueOf(12600.00), "40mm, steel, 1560cal", 1L);
    }

    @Test
    void testSaveWatchSuccess() {
        // Arrange
        when(imageService.findById(watchDto.getImageId())).thenReturn(image);
        when(watchMapper.toWatch(watchDto, image)).thenReturn(watch);
        when(watchRepository.save(watch)).thenReturn(watch);

        // Act
        Watch result = watchService.saveWatch(watchDto);

        // Assert
        assertEquals(watch, result, "The saved watch should match the expected watch");
        verify(imageService).findById(watchDto.getImageId());
        verify(watchMapper).toWatch(watchDto, image);
        verify(watchRepository).save(watch);
    }

    @Test
    void testSaveWatchDatabaseExceptionFail() {
        // Arrange
        when(imageService.findById(watchDto.getImageId())).thenReturn(image);
        when(watchMapper.toWatch(watchDto, image)).thenReturn(watch);
        when(watchRepository.save(watch)).thenThrow(new DatabaseException("Database error", new RuntimeException()));

        // Act & Assert
        DatabaseException exception = assertThrows(DatabaseException.class, () ->
                watchService.saveWatch(watchDto));
        assertTrue(exception.getMessage().contains("Database error occurred while saving watch with ID"));
        verify(watchRepository).save(watch);
    }

    @Test
    void testGetWatchByImageSuccess() {
        // Arrange
        when(watchRepository.findByImageId(image.getId())).thenReturn(Optional.of(watch));

        // Act
        Watch result = watchService.getWatchByImage(image.getId());

        // Assert
        assertEquals(watch, result, "The retrieved watch should match the expected watch");
        verify(watchRepository).findByImageId(image.getId());
    }

    @Test
    void testGetWatchByImageNotFoundFail() {
        // Arrange
        when(watchRepository.findByImageId(image.getId())).thenReturn(Optional.empty());

        // Act & Assert
        WatchNotFoundException exception = assertThrows(WatchNotFoundException.class, () ->
                watchService.getWatchByImage(image.getId()));
        assertEquals("Watch not found for image id: " + image.getId(), exception.getMessage());
    }

    @Test
    void testDeleteWatchSuccess() {
        // Act
        watchService.deleteWatch(watch.getId());

        // Assert
        verify(watchRepository).deleteById(watch.getId());
    }

    @Test
    void testDeleteWatchDatabaseExceptionFail() {
        // Arrange
        doThrow(new DatabaseException("Database error", new RuntimeException())).when(watchRepository).deleteById(watch.getId());

        // Act & Assert
        DatabaseException exception = assertThrows(DatabaseException.class, () ->
                watchService.deleteWatch(watch.getId()));
        assertTrue(exception.getMessage().contains("Database error occurred while deleting watch with ID"));
    }

    @Test
    void testGetWatchInfoSuccess() {
        // Arrange
        when(watchRepository.findById(watch.getId())).thenReturn(Optional.of(watch));

        // Act
        ResponseEntity<String> result = watchService.getWatchInfo(watch.getId());

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(watch.getId() + " " + watch.getName() + " " + watch.getBrand() + " " +
                watch.getPrice() + " " + watch.getImage().getId(), result.getBody());
    }

    @Test
    void testFindAllByOrderHighToLowSucces() {
        // Arrange
    	List<Watch> sortedWatches = new ArrayList<>();
    	sortedWatches.add(watch);
    	when(watchRepository.findAll(Sort.by(Sort.Order.desc("price")))).thenReturn(sortedWatches);

        // Act
        List<Watch> result = watchService.findAllByOrder("high-to-low");

        // Assert
        assertEquals(1, result.size());
        assertEquals(watch, result.get(0));
    }

    @Test
    void testGetAllWatchesSuccess() {
        // Arrange
    	List<Watch> sortedWatches = new ArrayList<>();
    	Watch mockWatch = Mockito.mock(Watch.class);
    	sortedWatches.add(watch);
    	sortedWatches.add(mockWatch);
        when(watchRepository.findAll()).thenReturn(sortedWatches);

        // Act
        List<Watch> result = watchService.getAllWatches();

        // Assert
        assertEquals(2, result.size());
        assertEquals(watch, result.get(0));
        verify(watchRepository).findAll();
    }
}
