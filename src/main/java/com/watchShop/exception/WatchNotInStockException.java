package com.watchShop.exception;

public class WatchNotInStockException extends Exception {

	private static final String DEFAULT_MESSAGE = "Watch is not in stock";

	public WatchNotInStockException() {
		super(DEFAULT_MESSAGE);
	}
}

