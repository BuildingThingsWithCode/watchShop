package com.watchShop.service;

import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Service;

import com.watchShop.repository.TokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
	
	private final TokenRepository tokenrepository;
	
	public CsrfTokenRepository getCsrfTokenRepository() {
		return tokenrepository.csrfTokenRepository();
	}
}
