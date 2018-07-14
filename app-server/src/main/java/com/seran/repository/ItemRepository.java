package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
	Item findByIsbn(String isbn);
}
