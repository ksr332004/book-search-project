package com.seran.service.impl;

import com.seran.dto.Document;
import com.seran.entity.Bookmark;
import com.seran.repository.BookmarkRepository;
import com.seran.repository.predicate.BookmarkSearchPredicate;
import com.seran.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    private BookmarkRepository bookmarkRepository;

    @Autowired
    public BookmarkServiceImpl(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    @Transactional
    public Page<Bookmark> searchBookmarks(Integer userId, String target, String query, Pageable pageable) {
        return bookmarkRepository.findAll(BookmarkSearchPredicate.searchBookmarkByUserIdAndTarget(userId, target, query), pageable);
    }

    @Override
    public Optional<Bookmark> searchBookmarkByUserIdAndKeyBarcode(Integer userId, String keyBarcode) {
        return Optional.ofNullable(bookmarkRepository.findByUserIdAndKeyBarcode(userId, keyBarcode));
    }

    @Override
    @Transactional
    public void saveBookmark(Integer userId, Document document) {
        String keyBarcode = (document.getBarcode().isEmpty()) ? document.getEbook_barcode() : document.getBarcode();

        Bookmark bookmark = new Bookmark();
        if (!searchBookmarkByUserIdAndKeyBarcode(userId, keyBarcode).isPresent()) {
            try {
                bookmark.setUserId(userId);
                bookmark.setKeyBarcode(keyBarcode);
                bookmark.setTitle(document.getTitle());
                bookmark.setContents(document.getContents());
                bookmark.setUrl(document.getUrl());
                bookmark.setIsbn(document.getIsbn());
                bookmark.setCreateDate(document.getDatetimeString());
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
                bookmarkRepository.save(bookmark);
            } catch (Exception e) {
                throw new BadCredentialsException("svae error.");
            }
        }
    }

    @Override
    public Optional<Bookmark> searchBookmarkByUserIdAndId(Integer userId, Integer id) {
        return Optional.ofNullable(bookmarkRepository.findByUserIdAndId(userId,id));
    }

    @Override
    @Transactional
    public void deleteBookmarkById(Integer id) {
        bookmarkRepository.deleteById(id);
    }

}
