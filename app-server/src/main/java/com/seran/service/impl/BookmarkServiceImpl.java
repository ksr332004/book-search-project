package com.seran.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seran.model.Bookmark;
import com.seran.model.Document;
import com.seran.repository.BookmarkRepository;
import com.seran.service.BookmarkService;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;
    
    @Override
    public Optional<Bookmark> findUserBookmark(Integer userId, String keyBarcode) {
        return Optional.ofNullable(bookmarkRepository.findByUserIdAndKeyBarcode(userId, keyBarcode));
    }
    
    @Override
    public Optional<Bookmark> findBookmark(Integer id) {
        return Optional.ofNullable(bookmarkRepository.findById(id).get());
    }
    
    @Override
    @Transactional
    public Optional<Bookmark> saveBookmark(Document document) {
        Integer userId = 123;
        String keyBarcode = (document.getBarcode().isEmpty()) ? document.getEbook_barcode() : document.getBarcode();
        
        Bookmark bookmark = new Bookmark();
        if (!findUserBookmark(userId, keyBarcode).isPresent()) {
            bookmark.setUserId(userId);
            bookmark.setKeyBarcode(keyBarcode);
            bookmark.setTitle(document.getTitle());
            bookmark.setContents(document.getContents());
            bookmark.setUrl(document.getUrl());
            bookmark.setIsbn(document.getIsbn());
            bookmark.setCreateDate(document.getDatetime());
            bookmark.setAuthors(document.getAuthorString());
            bookmark.setPublisher(document.getPublisher());
            bookmark.setTranslators(document.getTranslatorString());
            bookmark.setPrice(Integer.parseInt(document.getPrice()));
            bookmark.setSalePrice(Integer.parseInt(document.getSale_price()));
            bookmark.setSaleYN(document.getSale_yn());
            bookmark.setCategory(document.getCategory());
            bookmark.setThumbnail(document.getThumbnail());
            bookmark.setBarcode(document.getBarcode());
            bookmark.setEbookBarcode(document.getEbook_barcode());
            bookmark.setStatus(document.getStatus());
        }
        return Optional.ofNullable(bookmarkRepository.save(bookmark));
    }
    
    @Override
    @Transactional
    public void deleteByBookmark(Integer id) {
        bookmarkRepository.deleteById(id);
    }
    
}
