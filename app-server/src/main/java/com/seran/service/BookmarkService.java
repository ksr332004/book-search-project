package com.seran.service;

import java.util.Optional;

import com.seran.model.Bookmark;
import com.seran.model.Document;

public interface BookmarkService {

    Optional<Bookmark> searchBookmarkByUserIdAndKeyBarcode(Integer userId, String keyBarcode);
    Optional<Bookmark> searchBookmarkById(Integer id);
    void saveBookmark(Integer userId, Document document);
    void deleteBookmarkById(Integer id);
    
}
