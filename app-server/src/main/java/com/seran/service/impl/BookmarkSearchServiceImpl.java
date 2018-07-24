package com.seran.service.impl;

import com.seran.entity.Bookmark;
import com.seran.repository.BookmarkSearchRepository;
import com.seran.repository.predicate.BookmarkSearchPredicate;
import com.seran.service.BookmarkSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookmarkSearchServiceImpl implements BookmarkSearchService {

    private BookmarkSearchRepository bookmarkSearchRepository;

    @Autowired
    public BookmarkSearchServiceImpl(BookmarkSearchRepository bookmarkSearchRepository) {
        this.bookmarkSearchRepository = bookmarkSearchRepository;
    }

    @Override
    @Transactional
    public Page<Bookmark> searchBookmarks(Integer userId, String target, String query, Pageable pageable) {
        return bookmarkSearchRepository.findAll(BookmarkSearchPredicate.searchBookmarkByUserIdAndTarget(userId, target, query), pageable);
    }

}
