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
        Watch watch = new Watch();
        watch.setPrice(new BigDecimal("100.00"));

        cartService.add(watch);
        
        assertFalse(cartService.isEmpty());
        assertEquals(1, cartService.getAll().size());
        assertEquals(1, cartService.getAll().iterator().next().getValue());
    }

    @Test
    void testRemoveItemFromCart() {
        Watch watch = new Watch();
        watch.setPrice(new BigDecimal("100.00"));

        cartService.add(watch);
        cartService.remove(watch);

        assertTrue(cartService.isEmpty());
    }

    @Test
    void testGetTotal() {
        Watch watch1 = new Watch();
        watch1.setPrice(new BigDecimal("100.00"));
        Watch watch2 = new Watch();
        watch2.setPrice(new BigDecimal("200.00"));

        cartService.add(watch1);
        cartService.add(watch1);
        cartService.add(watch2);

        BigDecimal total = cartService.getTotal();
        assertEquals(new BigDecimal("400.00"), total);
    }

    @Test
    void testEmptyCart() {
        Watch watch = new Watch();
        watch.setPrice(new BigDecimal("100.00"));

        cartService.add(watch);
        cartService.emptyCart();

        assertTrue(cartService.isEmpty());
    }

    @Test
    void testRemoveItems() {
        Watch watch1 = new Watch();
        watch1.setId(1L);
        watch1.setPrice(new BigDecimal("100.00"));

        Watch watch2 = new Watch();
        watch2.setId(2L);
        watch2.setPrice(new BigDecimal("200.00"));

        cartService.add(watch1);
        cartService.add(watch2);

        when(watchService.getWatchById(1L)).thenReturn(watch1);
        when(watchService.getWatchById(2L)).thenReturn(watch2);
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        cartService.removeItems(list);

        assertTrue(cartService.isEmpty());
    }

    @Test
    void testRemoveItemWithMultipleQuantities() {
        Watch watch = new Watch();
        watch.setPrice(new BigDecimal("100.00"));

        cartService.add(watch);
        cartService.add(watch);

        cartService.remove(watch);

        assertFalse(cartService.isEmpty());
        assertEquals(1, cartService.getAll().iterator().next().getValue());
    }
}
