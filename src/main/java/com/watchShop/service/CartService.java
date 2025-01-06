package com.watchShop.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.watchShop.exception.WatchNotFoundException;
import com.watchShop.model.Watch;

public interface CartService {
	void add(Watch watch);
	void remove(Watch watch);
	void emptyCart();
	void removeItems(List<Long> itemIds);
	Set<Entry<Watch, Integer>> getAll();
	BigDecimal getTotal();
	Boolean isEmpty();
}
