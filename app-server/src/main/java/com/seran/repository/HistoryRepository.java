package com.seran.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seran.entity.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
	List<History> findTop10ByUserIdOrderByNewDateDesc(Integer userId);
}
