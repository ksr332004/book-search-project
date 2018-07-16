package com.seran.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seran.dto.Document;
import com.seran.entity.Bookmark;
import com.seran.entity.User;
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
	public ResponseEntity<Page<Bookmark>> getUserBookmark(Authentication authentication,
			@Valid @RequestParam(defaultValue = "title") String type,
			@Valid @RequestParam(required = false) String query,
			@Valid @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Direction.DESC) Pageable pageable) {
	    Optional<User> user = userService.searchUserByEmail(authentication.getPrincipal().toString());
        if (user.isPresent()) {
            Integer userId = user.get().getId();
            bookmarkSearchService = bookmarkSearchServiceFactory.getBookmarkSearchService(type);
            Page<Bookmark> bookmarks = bookmarkSearchService.searchBookmarks(userId, query, pageable);
            return new ResponseEntity<>(bookmarks, HttpStatus.OK);  
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Void> postUserBookmark(Authentication authentication, @Valid @RequestBody Document document) {
	    Optional<User> user = userService.searchUserByEmail(authentication.getPrincipal().toString());
	    bookmarkService.saveBookmark(user.get().getId(), document);
	    return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserBookmark(@Valid @PathVariable Integer id) {
        Optional<Bookmark> bookMark = bookmarkService.searchBookmarkById(id);
        if (bookMark.isPresent()) {
            bookmarkService.deleteBookmarkById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
