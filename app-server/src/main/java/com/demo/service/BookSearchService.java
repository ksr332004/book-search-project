package com.demo.service;

import java.util.Optional;

import com.demo.model.Book;
import com.demo.model.Parameter;

public interface BookSearchService {

    Optional<Book> searchBook(Parameter parameter);
    
}
