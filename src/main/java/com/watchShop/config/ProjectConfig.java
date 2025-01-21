package com.watchShop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

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
		.antMatchers("/", "/register", "/css/**", "/images/**", "/login", "/logout").permitAll()
		.antMatchers("/cart", "/checkout").authenticated()
		.antMatchers("/admin/**").hasAuthority("ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin(form -> form
				.loginPage("/login")   
				.loginProcessingUrl("/authentication") 
				.permitAll())
		.logout(logout -> logout
				.logoutUrl("/logout") 
				.logoutSuccessUrl("/login") 
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll()
				)
		.exceptionHandling()
		.accessDeniedPage("/noaccess");

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(myUserDetailsService)
				.passwordEncoder(passwordEncoder())
				.and()
				.build();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}



