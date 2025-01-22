package com.watchShop.service;

import java.util.Collections;
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

    public Map<String, String> getQuote() {
        try {
            List<Map<String, String>> list = restTemplate.getForObject(url, List.class);
            
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            } else {
                return Collections.singletonMap("quote", "No quote available today.");
            }
        } catch (Exception e) {
            return Collections.singletonMap("quote", "No quote available today.");
        }
    }
}
