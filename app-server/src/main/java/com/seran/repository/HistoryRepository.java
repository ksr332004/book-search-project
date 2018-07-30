package com.seran.repository;

import com.seran.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
	List<History> findTop20ByUserIdOrderByNewDateDesc(Integer userId);
	Optional<History> findByUserIdAndSearch(Integer userId, String search);
}
