package com.seran.controller;

import com.seran.auth.AuthUtil;
import com.seran.dto.Book;
import com.seran.dto.Parameter;
import com.seran.entity.History;
import com.seran.service.BookSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/search")
public class BookSearchController {

    @Autowired
    private BookSearchService searchService;
    
    @PostMapping("/book")
    public ResponseEntity<Book> getSearchList(Authentication authentication, @Valid @RequestBody Parameter parameter) {
        Optional<Book> book = searchService.searchBooks(parameter);
        
        if (!Optional.ofNullable(parameter.getQuery()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Integer userId = AuthUtil.getUserId(authentication);
        searchService.saveSearchHistory(userId, parameter);

        if (parameter.getSize() != null && 
        		(parameter.getSize() <= 0 || parameter.getSize() > 50)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (parameter.getPage() != null &&
        		(parameter.getPage() <= 0 || parameter.getPage() > 50)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

	    return book.map(b -> new ResponseEntity<>(b, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/history")
    public ResponseEntity<List<History>> getHistorys(Authentication authentication) {
        Integer userId = AuthUtil.getUserId(authentication);
        return new ResponseEntity<>(searchService.searchHistorys(userId), HttpStatus.OK);
    }
    
}
