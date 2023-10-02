package com.acevba.springapi.business.abstracts;

import com.acevba.springapi.domain.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractLongIdService<E extends DomainEntity<Long>> extends AbstractService<E, Long> {
    public AbstractLongIdService(JpaRepository<E, Long> repository) {
        super(repository);
    }
}
