package com.seran.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seran.entity.Bookmark;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
    
    Bookmark findByUserIdAndKeyBarcode(Integer userId, String keyBarcode);
    
}
