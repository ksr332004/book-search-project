package com.seran.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seran.model.Bookmark;
import com.seran.model.Document;
import com.seran.model.User;
import com.seran.service.BookmarkSearchService;
import com.seran.service.BookmarkService;
import com.seran.service.UserService;
import com.seran.service.factory.BookmarkSearchServiceFactory;

@RestController
@RequestMapping("/api/bookmark")
public class BookmarkController {

    @Autowired
    private UserService userService;
	@Autowired
	private BookmarkService bookmarkService;
	@Autowired
	private BookmarkSearchServiceFactory bookmarkSearchServiceFactory;
	
	private BookmarkSearchService bookmarkSearchService;
	
	@GetMapping("/view")
	public ResponseEntity<Page<Bookmark>> getUserBookmark(
	        @RequestParam(defaultValue = "title") String type,
            @RequestParam(required = true) String query,
	        @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Direction.DESC) Pageable pageable) {
	    // TODO : 로그인 세션 연동
	    Optional<User> user = userService.findUserById(123);
        if (user.isPresent()) {
            Integer userId = user.get().getId();
            bookmarkSearchService = bookmarkSearchServiceFactory.getBookmarkSearchService(type);
            Page<Bookmark> bookmarks = bookmarkSearchService.findBookmarks(userId, query, pageable);
            return new ResponseEntity<>(bookmarks, HttpStatus.OK);  
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Void> postUserBookmark(@RequestBody Document document) {
	    Optional<Bookmark> bookmark = bookmarkService.saveBookmark(document);
	    return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserBookmark(@PathVariable Integer id) {
        Optional<Bookmark> bookMark = bookmarkService.findBookmark(id);
        if (bookMark.isPresent()) {
            bookmarkService.deleteByBookmark(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
