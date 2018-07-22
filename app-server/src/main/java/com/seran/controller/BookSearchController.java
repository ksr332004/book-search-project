package com.seran.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seran.dto.Book;
import com.seran.dto.Parameter;
import com.seran.entity.History;
import com.seran.entity.User;
import com.seran.service.BookSearchService;
import com.seran.service.UserService;

@RestController
@RequestMapping("/api/search")
public class BookSearchController {

	@Autowired
	private UserService userService;
    @Autowired
    private BookSearchService searchService;
    
    @PostMapping("/book")
    public ResponseEntity<Book> getSearchList(Authentication authentication, @Valid @RequestBody Parameter parameter) {
        Optional<Book> book = searchService.searchBooks(parameter);
        
        if (!Optional.ofNullable(parameter.getQuery()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        Optional<User> user = userService.searchUserByEmail(authentication.getPrincipal().toString());
        if (user.isPresent()) {
            searchService.saveSearchHistory(user.get().getId(), parameter);
        }
        
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
        Optional<User> user = userService.searchUserByEmail(authentication.getPrincipal().toString());
	    return user.map(u -> new ResponseEntity<>(searchService.searchHistorys(u.getId()), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
    
}
