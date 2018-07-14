package com.seran.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.seran.model.Bookmark;

public interface BookmarkSearchService {

    Page<Bookmark> findBookmarks(Integer userId, String query, Pageable pageable);
    
}
