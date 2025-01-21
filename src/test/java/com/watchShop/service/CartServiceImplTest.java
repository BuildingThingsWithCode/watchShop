package com.watchShop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.watchShop.model.Watch;

class CartServiceImplTest {

    @Mock
    private WatchService watchService;

    @InjectMocks
    private CartServiceImpl cartService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddItemToCart() {
    	// Arrange
        Watch watch = new Watch();
        
        // Act
        cartService.add(watch);
        
        // Assert
        assertFalse(cartService.isEmpty());
        assertEquals(1, cartService.getAll().size());
        assertEquals(1, cartService.getAll().iterator().next().getValue());
    }

    @Test
    void testRemoveItemFromCart() {
    	// Arrange
        Watch watch = new Watch();
        cartService.add(watch);
        
        // Act
        cartService.remove(watch);

        // Assert
        assertTrue(cartService.isEmpty());
    }

    @Test
    void testGetTotal() {
    	// Arrange
        Watch watch1 = new Watch();
        watch1.setPrice(new BigDecimal("100.00"));
        Watch watch2 = new Watch();
        watch2.setPrice(new BigDecimal("200.00"));
        cartService.add(watch1);
        cartService.add(watch1);
        cartService.add(watch2);
        
        // Act
        BigDecimal total = cartService.getTotal();
        
        // Assert
        assertEquals(new BigDecimal("400.00"), total);
    }

    @Test
    void testEmptyCart() {
    	// Arrange
        Watch watch = new Watch();
        cartService.add(watch);
        
        // Act
        cartService.emptyCart();
        
        // Assert
        assertTrue(cartService.isEmpty());
    }

    @Test
    void testRemoveItems() {
    	// Arrange
        Watch watch1 = new Watch();
        watch1.setId(1L);
        Watch watch2 = new Watch();
        watch2.setId(2L);
        cartService.add(watch1);
        cartService.add(watch2);
        when(watchService.getWatchById(1L)).thenReturn(watch1);
        when(watchService.getWatchById(2L)).thenReturn(watch2);
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        
        // Act
        cartService.removeItems(list);
        
        // Assert
        assertTrue(cartService.isEmpty());
    }

    @Test
    void testRemoveItemWithMultipleQuantities() {
    	// Arrange
        Watch watch = new Watch();
        cartService.add(watch);
        cartService.add(watch);
        
        // Act
        cartService.remove(watch);
        
        // Assert
        assertFalse(cartService.isEmpty());
        assertEquals(1, cartService.getAll().iterator().next().getValue());
    }
}
