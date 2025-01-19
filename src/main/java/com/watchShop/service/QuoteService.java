package com.watchShop.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Data
public class QuoteService {

	private final RestTemplate restTemplate;
	private final String url = "https://zenquotes.io/api/today";
	
	public Map<String, String> getQuote(){
		List<Map<String, String>> list = restTemplate.getForObject(url, List.class);
		return list.get(0);
	}
	
}
