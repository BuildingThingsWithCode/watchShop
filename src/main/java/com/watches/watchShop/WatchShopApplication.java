package com.watches.watchShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"controllers", "model", "repository", "service"})
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "model") 
public class WatchShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchShopApplication.class, args);
	}
}
