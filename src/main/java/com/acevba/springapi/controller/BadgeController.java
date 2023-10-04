package com.acevba.springapi.controller;

import com.acevba.springapi.exception.ResourceNotFoundException;
import com.acevba.springapi.model.Badge;
import com.acevba.springapi.repository.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BadgeController {

    @Autowired
    private BadgeRepository badgeRepository;

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
}