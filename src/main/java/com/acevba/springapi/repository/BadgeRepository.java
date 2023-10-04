package com.acevba.springapi.repository;

import com.acevba.springapi.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {}
