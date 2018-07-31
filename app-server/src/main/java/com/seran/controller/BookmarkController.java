package com.seran.controller;

import com.seran.auth.AuthUtil;
import com.seran.dto.Document;
import com.seran.entity.Bookmark;
import com.seran.service.BookmarkService;
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

    private final BookmarkService bookmarkService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @GetMapping("/view")
    public ResponseEntity<Page<Bookmark>> getUserBookmark(Authentication authentication,
                                                          @RequestParam(defaultValue = "title", required = false) String target,
                                                          @RequestParam(defaultValue = "none", required = false) String query,
                                                          @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Direction.DESC) Pageable pageable) {
        Integer userId = AuthUtil.getUserId(authentication);
        Page<Bookmark> bookmarks = bookmarkService.searchBookmarks(userId, target, query, pageable);
        return new ResponseEntity<>(bookmarks, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> postUserBookmark(Authentication authentication, @Valid @RequestBody Document document) {
        Integer userId = AuthUtil.getUserId(authentication);
        String keyBarcode = (document.getBarcode().isEmpty()) ? document.getEbook_barcode() : document.getBarcode();
        Optional<Bookmark> bookmark = bookmarkService.searchBookmarkByUserIdAndKeyBarcode(userId, keyBarcode);
        if (bookmark.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        bookmarkService.saveBookmark(userId, document);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserBookmark(Authentication authentication, @Valid @PathVariable Integer id) {
        Integer userId = AuthUtil.getUserId(authentication);
        Optional<Bookmark> bookmark = bookmarkService.searchBookmarkByUserIdAndId(userId, id);
        if (bookmark.isPresent()) {
            bookmarkService.deleteBookmarkById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
