package com.watchShop.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.watchShop.model.Watch;

import lombok.RequiredArgsConstructor;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

	private final WatchService watchService;
	private Map<Watch, Integer> items = new HashMap<>();

	@Override
	public void add(Watch watch) {
		items.put(watch, items.getOrDefault(watch, 0) + 1);
	}

	@Override
	public void remove(Watch watch) {
		items.computeIfPresent(watch, (key, count) -> count == 1 ? null : count - 1);
	}

	@Override
	public Set<Entry<Watch, Integer>> getAll() {
		return items.entrySet();
	}

	@Override
	public BigDecimal getTotal() {
		BigDecimal sum = BigDecimal.ZERO;
		for (Map.Entry<Watch, Integer> entry : items.entrySet()) {
			sum = sum.add(entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue())));
		}
		return sum;
	}

	@Override
	public void emptyCart() {
		items.clear();
	}

	@Override
	public void removeItems(List<Long> itemIds) {
		for (Long id : itemIds) {
			items.remove(watchService.getWatchById(id));
		}
	}

	@Override
	public Boolean isEmpty() {
		return items.isEmpty();
	}
}
