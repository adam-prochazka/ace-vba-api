package com.acevba.springapi.business.abstracts;

import com.acevba.springapi.model.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public abstract class AbstractService<E extends DomainEntity<K>, K> {
    protected final JpaRepository<E, K> repository;

    public AbstractService(JpaRepository<E, K> repository) {
        this.repository = repository;
    }

    public E create(E e) {
        K k = e.getId();
        if (k != null && repository.existsById(k))
            throw new RuntimeException("Given id already exists in DB!");
        return repository.save(e);
    }

    public Collection<E> getAll() {
        return repository.findAll();
    }

    public Optional<E> getById(K k) {
        if (k == null)
            throw new RuntimeException("Id is null.");
        return repository.findById(k);
    }


    public E update(E e, K k) {
        if (repository.existsById(k))
            return repository.save(e);
        else
            throw new RuntimeException("Id does not exist");
    }

    public void deleteById(K k) {
        repository.deleteById(k);
    }
}
