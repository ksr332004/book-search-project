package com.seran.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seran.model.Book;
import com.seran.model.History;
import com.seran.model.Parameter;
import com.seran.model.User;
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
    public ResponseEntity<Book> getSearchList(Authentication authentication, @RequestBody Parameter parameter) {
        Optional<Book> book = searchService.searchBooks(parameter);
        
        if (!Optional.ofNullable(parameter.getQuery()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        Optional<User> user = userService.searchUserByEmail(authentication.getPrincipal().toString());
        if (user.isPresent()) {
            searchService.saveSearchHistory(user.get().getId(), parameter.getQuery());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        
        if (parameter.getSize() != null && 
        		(parameter.getSize() <= 0 || parameter.getSize() > 50)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (parameter.getPage() != null &&
        		(parameter.getPage() <= 0 || parameter.getPage() > 50)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        if (book.isPresent()) {
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/history")
    public ResponseEntity<List<History>> getHistorys(Authentication authentication) {
        Optional<User> user = userService.searchUserByEmail(authentication.getPrincipal().toString());
    	if (user.isPresent()) {
    		return new ResponseEntity<>(searchService.searchHistorys(user.get().getId()), HttpStatus.OK);
    	}
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
