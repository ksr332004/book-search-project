package com.seran.controller;

import com.seran.dto.Document;
import com.seran.entity.Bookmark;
import com.seran.entity.User;
import com.seran.service.BookmarkSearchService;
import com.seran.service.BookmarkService;
import com.seran.service.UserService;
import com.seran.service.factory.BookmarkSearchServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
		user.ifPresent(u -> bookmarkService.saveBookmark(u.getId(), document));
	    return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserBookmark(Authentication authentication, @Valid @PathVariable Integer id) {
		Optional<User> user = userService.searchUserByEmail(authentication.getPrincipal().toString());
        Optional<Bookmark> bookMark = bookmarkService.searchBookmarkByUserIdAndId(user.get().getId(), id);
        if (bookMark.isPresent()) {
			bookmarkService.deleteBookmarkById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
