package com.watchShop.config;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.spring5.view.AbstractThymeleafView;

import com.watchShop.service.MyUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ProjectConfig  {

	private final MyUserDetailsService myUserDetailsService;

	@Bean 
	public AuthenticationProvider authenticationProvider(@Autowired PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(myUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests()
		.antMatchers("/", "/register", "/css/**", "/images/**", "/login", "/info", "/base").permitAll()
		.antMatchers("/cart", "/checkout").authenticated()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin(form -> form
	            .loginPage("/login")               // Custom login page endpoint
	            .loginProcessingUrl("/authentication") // Custom authentication endpoint
	            .permitAll());
//		.securityContext(securityContext -> securityContext
//		        .requireExplicitSave(false)); 

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		System.out.println("AuthenticationManager is being called");
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(myUserDetailsService)
				.passwordEncoder(passwordEncoder())
				.and()
				.build();
	}

}



