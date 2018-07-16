package com.seran.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.seran.entity.Bookmark;

public interface BookmarkSearchService {

    Page<Bookmark> searchBookmarks(Integer userId, String query, Pageable pageable);
    
}
