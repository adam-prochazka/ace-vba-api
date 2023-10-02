package com.acevba.springapi.business;

import com.acevba.springapi.business.abstracts.AbstractLongIdService;
import com.acevba.springapi.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class BadgeService extends AbstractLongIdService<Badge> {

    public BadgeService(JpaRepository<Badge, Long> repository) {
        super(repository);
    }
}
