package com.seran.repository;

import com.seran.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkSearchRepository extends JpaRepository<Bookmark, Integer>, QuerydslPredicateExecutor<Bookmark> {
}
