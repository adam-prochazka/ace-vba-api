package com.acevba.springapi.business;

import com.acevba.springapi.business.abstracts.AbstractLongIdService;
import com.acevba.springapi.dao.UserRepository;
import com.acevba.springapi.domain.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class UserService extends AbstractLongIdService<User> {


    public UserService(UserRepository repository) {
        super(repository);
    }

    public Collection<User> getByUsername(String username) {
        return repository.findAll().stream().filter(e -> Objects.equals(e.getUsername(), username)).toList();
    }

}
