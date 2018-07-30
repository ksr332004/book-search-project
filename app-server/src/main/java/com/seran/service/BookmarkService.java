package com.seran.service;

import com.seran.dto.Document;
import com.seran.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookmarkService {
    Page<Bookmark> searchBookmarks(Integer userId, String target, String query, Pageable pageable);
    Optional<Bookmark> searchBookmarkByUserIdAndKeyBarcode(Integer userId, String keyBarcode);
    Optional<Bookmark> searchBookmarkByUserIdAndId(Integer userId, Integer id);
    void saveBookmark(Integer userId, Document document);
    void deleteBookmarkById(Integer id);
}
