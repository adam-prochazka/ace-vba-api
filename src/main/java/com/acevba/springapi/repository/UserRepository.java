package com.acevba.springapi.repository;

import com.acevba.springapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findByUsername (String username);
}