package com.acevba.springapi.dao;

import com.acevba.springapi.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {}
