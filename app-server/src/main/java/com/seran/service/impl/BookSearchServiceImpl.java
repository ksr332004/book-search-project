package com.seran.service.impl;

import com.seran.dto.Book;
import com.seran.dto.SearchInfo;
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
import java.time.LocalDateTime;
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
    public Optional<Book> searchBooks(SearchInfo searchInfo) {
        ResponseEntity<Book> responseEntity = null;
        responseEntity = getBookSearchResult(searchInfo);
        return Optional.ofNullable(responseEntity.getBody());
    }
    
    @Override
    @Transactional
    public void saveSearchHistory(Integer userId, SearchInfo searchInfo) {
        try {
            History history;
            Optional<History> historyOptional = historyRepository.findByUserIdAndSearch(userId, searchInfo.getQuery());
            // 기존에 존재할 경우 업데이트
            if (historyOptional.isPresent()) {
                history = historyOptional.get();
                history.setNewDate(LocalDateTime.now());
            } else {
                history = new History();
                history.setUserId(userId);
                history.setSearch(searchInfo.getQuery());
                history.setTarget(searchInfo.getTarget());
            }
            historyRepository.save(history);
        } catch (Exception e) {
            throw new BadCredentialsException("insert error.");
        }
    }
    
    private ResponseEntity<Book> getBookSearchResult(SearchInfo searchInfo) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        httpHeaders.add("Authorization", "KakaoAK " + apiKey);
        return restTemplate.exchange(searchInfo.getUrl(apiUrl), HttpMethod.GET, new HttpEntity<>(httpHeaders), Book.class);
    }
    
    @Override
    public List<History> searchHistorys(Integer userId) {
        return historyRepository.findTop20ByUserIdOrderByNewDateDesc(userId);
    }
    
}
