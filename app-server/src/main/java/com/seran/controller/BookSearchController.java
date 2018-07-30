package com.seran.controller;

import com.seran.auth.AuthUtil;
import com.seran.dto.Book;
import com.seran.dto.SearchInfo;
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
    public ResponseEntity<Book> getSearchList(Authentication authentication, @Valid @RequestBody SearchInfo searchInfo) {
        Optional<Book> book = searchService.searchBooks(searchInfo);
        
        if (!Optional.ofNullable(searchInfo.getQuery()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Integer userId = AuthUtil.getUserId(authentication);
        searchService.saveSearchHistory(userId, searchInfo);

        if (searchInfo.getSize() != null &&
        		(searchInfo.getSize() <= 0 || searchInfo.getSize() > 50)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (searchInfo.getPage() != null &&
        		(searchInfo.getPage() <= 0 || searchInfo.getPage() > 50)) {
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
