package com.seran.service.impl;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.seran.model.Book;
import com.seran.model.History;
import com.seran.model.Parameter;
import com.seran.repository.HistoryRepository;
import com.seran.service.BookSearchService;

@Service
public class BookSearchServiceImpl implements BookSearchService {

    @Value("${book.api.url}")
    private String apiUrl;
    @Value("${book.api.key}")
    private String apiKey;
    
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HistoryRepository historyRepository;
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Override
    public Optional<Book> searchBooks(Parameter parameter) {
        ResponseEntity<Book> responseEntity = null;
        responseEntity = getBookSearchResult(parameter);
        return Optional.ofNullable(responseEntity.getBody());
    }
    
    @Override
    @Transactional
    public void saveSearchHistory(Integer userId, String query) {
        try {
            History history = new History();
            history.setUserId(userId);
            history.setSearch(query);
            historyRepository.save(history);
        } catch (Exception e) {
            throw new BadCredentialsException("Bad request.");
        }
    }
    
    private ResponseEntity<Book> getBookSearchResult(Parameter parameter) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        httpHeaders.add("Authorization", "KakaoAK " + apiKey);
        return restTemplate.exchange(parameter.getUrl(apiUrl), HttpMethod.GET, new HttpEntity<>(httpHeaders), Book.class);
    }
    
    @Override
    public List<History> searchHistorys(Integer userId) {
        return historyRepository.findTop10ByUserIdOrderByNewDateDesc(userId);
    }
    
}
