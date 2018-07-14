package com.seran.service;

import java.util.Optional;

import com.seran.model.Book;
import com.seran.model.Parameter;

public interface BookSearchService {

    Optional<Book> searchBook(Parameter parameter);
    
}
