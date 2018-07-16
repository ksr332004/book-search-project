package com.seran.service.impl;

import com.seran.dto.Book;
import com.seran.dto.Parameter;
import com.seran.entity.History;
import com.seran.repository.HistoryRepository;
import com.seran.service.BookSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

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
            throw new BadCredentialsException("insert error.");
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
