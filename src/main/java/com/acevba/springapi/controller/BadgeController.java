package com.acevba.springapi.controller;

import com.acevba.springapi.exception.ResourceNotFoundException;
import com.acevba.springapi.model.Badge;
import com.acevba.springapi.model.User;
import com.acevba.springapi.repository.BadgeRepository;
import com.acevba.springapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class BadgeController {

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/badges")
    public ResponseEntity<List<Badge>> getAllBadges() {
        List<Badge> badges = new ArrayList<>();
        badges.addAll(badgeRepository.findAll());
        return new ResponseEntity<>(badges, HttpStatus.OK);
    }

    @GetMapping("/badges/{id}")
    public ResponseEntity<Badge> getBadgeById(@PathVariable Long id) {
        return new ResponseEntity<>(badgeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Badge with id = " + id)), HttpStatus.OK);
    }

    @PostMapping("/badges")
    public ResponseEntity<Badge> createBadge(@RequestBody Badge badge) {
        return new ResponseEntity<>(badgeRepository.save(badge), HttpStatus.CREATED);
    }

    /**
     * Preserve id
     */
    @PutMapping("/badges/{id}")
    public ResponseEntity<Badge> updateBadge(@RequestBody Badge badge, @PathVariable Long id) {
        Badge _badge = badgeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Badge with id = " + id));
        _badge.setLevel(badge.getLevel());
        _badge.setRole(badge.getRole());
        _badge.setUsers(badge.getUsers());
        return new ResponseEntity<>(badgeRepository.save(_badge), HttpStatus.OK);
    }

    @DeleteMapping("/badges/{id}")
    public ResponseEntity<HttpStatus> deleteBadge(@PathVariable Long id) {
        badgeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/badges")
    public ResponseEntity<HttpStatus> deleteAllBadges() {
        badgeRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/users/{userId}/badges")
    public ResponseEntity<Set<Badge>> getAllBadgesByUserId(
            @PathVariable(value = "userId") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id = " + userId));
        return new ResponseEntity<>(user.getBadges(), HttpStatus.OK);
    }

    @GetMapping("/badges/{badgeId}/users")
    public ResponseEntity<Set<User>> getAllUsersByBadgeId(
            @PathVariable(value = "badgeId") Long badgeId) {
        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Badge with id = " + badgeId));

        return new ResponseEntity<>(badge.getUsers(), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/badges")
    public ResponseEntity<Badge> addBadge(
            @PathVariable(value = "userId") Long userId,
            @RequestBody Badge badgeReq) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id = " + userId));

        Badge badge;

        if (badgeReq.getId() == null)
            badge = badgeRepository.save(badgeReq);

        else
            badge = badgeRepository.findById(badgeReq.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Badge with id = " + badgeReq.getId()));

        user.addBadge(badge);
        userRepository.save(user);
        return new ResponseEntity<>(badge, HttpStatus.CREATED);
    }

    }
}