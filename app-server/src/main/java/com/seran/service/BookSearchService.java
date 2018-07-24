package com.seran.service;

import java.util.List;
import java.util.Optional;

import com.seran.dto.Book;
import com.seran.dto.Parameter;
import com.seran.entity.History;

public interface BookSearchService {
    Optional<Book> searchBooks(Parameter parameter);
    List<History> searchHistorys(Integer userId);
    void saveSearchHistory(Integer userId, Parameter parameter);
}
