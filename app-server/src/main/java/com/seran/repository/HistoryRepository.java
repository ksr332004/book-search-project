package com.seran.repository;

import com.seran.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
	List<History> findTop10ByUserIdOrderByNewDateDesc(Integer userId);
}
