package com.seran.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.seran.entity.Bookmark;
import com.seran.repository.BookmarkSearchRepository;
import com.seran.service.BookmarkSearchService;

@Service
public class BookmarkContentSearchServiceImpl implements BookmarkSearchService {

    private BookmarkSearchRepository bookmarkSearchRepository;

    @Autowired
    public BookmarkContentSearchServiceImpl(BookmarkSearchRepository bookmarkSearchRepository) {
        this.bookmarkSearchRepository = bookmarkSearchRepository;
    }

    @Override
    public Page<Bookmark> searchBookmarks(Integer userId, String query, Pageable pageable) {
        return bookmarkSearchRepository.findByUserIdAndContents(userId, query, pageable);
    }
    
}
