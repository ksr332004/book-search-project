package com.seran.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seran.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(Integer id);
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
    
}