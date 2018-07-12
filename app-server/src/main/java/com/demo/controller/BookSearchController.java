package com.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Book;
import com.demo.model.Parameter;
import com.demo.service.BookSearchService;

@RestController
@RequestMapping("/api/book")
public class BookSearchController {

    @Autowired
    private BookSearchService searchService;
    
    @PostMapping("/search")
    public ResponseEntity<Book> getSearchList(@RequestBody Parameter parameter) {
        Optional<Book> book = searchService.searchBook(parameter);
        
        if (!Optional.ofNullable(parameter.getQuery()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
    
}
