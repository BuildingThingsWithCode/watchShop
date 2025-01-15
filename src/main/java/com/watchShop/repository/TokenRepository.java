package com.watchShop.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository {
	
	
//	public CsrfTokenRepository csrfTokenRepository() {
//	    CookieCsrfTokenRepository repository = CookieCsrfTokenRepository.withHttpOnlyFalse();
//	    repository.setCookieName("XSRF-TOKEN");
//	    return repository;
//	}
}
