package com.acevba.springapi.controller;

import com.acevba.springapi.exception.ResourceNotFoundException;
import com.acevba.springapi.repository.UserRepository;
import com.acevba.springapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers(@RequestParam Optional<String> username) {
        List<User> users = new ArrayList<>();

        if (username.isPresent())
            users.addAll(userRepository.findByUsername(username.get()));
        else
            users.addAll(userRepository.findAll());
        return users;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id = " + id));
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User user, @PathVariable Long id) {
        User _user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id = " + id));
        _user.setUsername(user.getUsername());
        _user.setBadges(user.getBadges());
        _user.setEvents(user.getEvents());
        return userRepository.save(_user);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @DeleteMapping("/users")
    void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
