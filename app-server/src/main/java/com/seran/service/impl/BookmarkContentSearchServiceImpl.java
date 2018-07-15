package com.seran.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.seran.model.Bookmark;
import com.seran.repository.BookmarkSearchRepository;
import com.seran.service.BookmarkSearchService;

@Service("content")
public class BookmarkContentSearchServiceImpl implements BookmarkSearchService {

    @Autowired
    private BookmarkSearchRepository bookmarkSearchRepository;
    
    @Override
    public Page<Bookmark> searchBookmarks(Integer userId, String query, Pageable pageable) {
        return bookmarkSearchRepository.findByUserIdAndContents(userId, query, pageable);
    }
    
}
