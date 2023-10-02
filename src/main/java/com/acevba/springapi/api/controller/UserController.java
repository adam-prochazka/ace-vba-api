package com.acevba.springapi.api.controller;

import com.acevba.springapi.business.UserService;
import com.acevba.springapi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public User create(@RequestBody User user) {
        try {
            return userService.create(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    
    @GetMapping("/users")
    public Collection<User> getAll(@RequestParam Optional<String> username) {
        Collection<User> users;
        if (username.isPresent())
            users = userService.getByUsername(username.get());
        else
            users = userService.getAll();
        return users.stream().toList();
    }

    @GetMapping("/users/{id}")
    public User getOne(@PathVariable Long id) {
        Optional<User> optionalUser = userService.getById(id);
        return optionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/users/{id}")
    User update(@RequestBody User newUser, @PathVariable Long id) {
        try {
            return userService.update(newUser, id);
        } catch (RuntimeException runtimeException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
