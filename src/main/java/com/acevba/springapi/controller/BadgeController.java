package com.acevba.springapi.controller;

import com.acevba.springapi.exception.ResourceNotFoundException;
import com.acevba.springapi.model.Badge;
import com.acevba.springapi.repository.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Badge> getAllBadges() {
        List<Badge> badges = new ArrayList<>();
        badges.addAll(badgeRepository.findAll());
        return badges;
    }

    @GetMapping("/badges/{id}")
    public Badge getBadgeById(@PathVariable Long id) {
        return badgeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Badge with id = " + id));
    }

    @PostMapping("/badges")
    public Badge createBadge(@RequestBody Badge badge) {
        return badgeRepository.save(badge);
    }

    /**
     * Preserve id
     */
    @PutMapping("/badges/{id}")
    Badge updateBadge(@RequestBody Badge badge, @PathVariable Long id) {
        Badge _badge = badgeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Badge with id = " + id));
        _badge.setLevel(badge.getLevel());
        _badge.setRole(badge.getRole());
        _badge.setUsers(badge.getUsers());
        return badgeRepository.save(_badge);
    }

    @DeleteMapping("/badges/{id}")
    void deleteBadge(@PathVariable Long id) {
        badgeRepository.deleteById(id);
    }

    @DeleteMapping("/badges")
    void deleteAllBadges() {
        badgeRepository.deleteAll();
    }
}