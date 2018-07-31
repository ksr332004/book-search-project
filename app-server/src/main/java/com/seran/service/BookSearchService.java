package com.seran.service;

import com.seran.dto.Book;
import com.seran.dto.SearchInfo;
import com.seran.entity.History;

import java.util.List;
import java.util.Optional;

public interface BookSearchService {
    Optional<Book> searchBooks(SearchInfo searchInfo);
    List<History> searchHistorys(Integer userId);
    void saveSearchHistory(Integer userId, SearchInfo searchInfo);
}
