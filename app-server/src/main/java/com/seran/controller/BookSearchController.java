package com.seran.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @CrossOrigin
    @PostMapping("/book")
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
    
    //  TODO : 세션으로 id 값을 받아오도록 구현해야 함
    //  @GetMapping("/history")
    //  public ResponseEntity<List<History>> getHistorys() {
    @GetMapping("/history/{id}")
    public ResponseEntity<List<History>> getHistorys(@PathVariable("id") Integer id) {
    	Optional<User> user = userService.findUserById(id);
    	if (user.isPresent()) {
    		return new ResponseEntity<>(userService.findHistorys(id), HttpStatus.OK);
    	}
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
