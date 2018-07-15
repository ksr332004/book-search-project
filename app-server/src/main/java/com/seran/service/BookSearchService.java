package com.seran.service;

import java.util.List;
import java.util.Optional;

import com.seran.model.Book;
import com.seran.model.History;
import com.seran.model.Parameter;

public interface BookSearchService {

    Optional<Book> searchBooks(Parameter parameter);
    List<History> searchHistorys(Integer userId);
    void saveSearchHistory(Integer userId, String query);
    
}
