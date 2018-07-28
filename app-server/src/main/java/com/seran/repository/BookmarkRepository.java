package com.seran.repository;

import com.seran.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer>, QuerydslPredicateExecutor<Bookmark> {
    Bookmark findByUserIdAndId(Integer userId, Integer id);
    Bookmark findByUserIdAndKeyBarcode(Integer userId, String keyBarcode);
}
