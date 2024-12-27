package com.watches.watchShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"controllers", "service"})
public class WatchShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchShopApplication.class, args);
	}
}
