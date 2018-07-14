package com.seran.service;

import java.util.Optional;

import com.seran.model.Bookmark;
import com.seran.model.Document;

public interface BookmarkService {

    Optional<Bookmark> findUserBookmark(Integer userId, String keyBarcode);
  
    Optional<Bookmark> findBookmark(Integer id);
  
    Optional<Bookmark> saveBookmark(Document document);
  
    void deleteByBookmark(Integer id);
    
}
