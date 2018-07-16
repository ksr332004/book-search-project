package com.seran.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seran.entity.Bookmark;

@Repository
public interface BookmarkSearchRepository extends JpaRepository<Bookmark, Integer> {
    
    Page<Bookmark> findByUserId(Integer userId, Pageable pageable);
    
    Page<Bookmark> findByUserIdAndTitle(Integer userId, String query, Pageable pageable);
    
    Page<Bookmark> findByUserIdAndContents(Integer userId, String query, Pageable pageable);

}
