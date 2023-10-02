package com.acevba.springapi.api.controller;

import com.acevba.springapi.business.BadgeService;
import com.acevba.springapi.domain.Badge;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/badges")
public class BadgeController {
    private final BadgeService badgeService;

    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @PostMapping
    public Badge create(@RequestBody Badge badge) {
        try {
            return badgeService.create(badge);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    
    @GetMapping
    public Collection<Badge> getAll() {
        return badgeService.getAll().stream().toList();
    }

    @GetMapping("/{id}")
    public Badge getOne(@PathVariable Long id) {
        Optional<Badge> optionalBadge = badgeService.getById(id);
        return optionalBadge.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    Badge update(@RequestBody Badge newBadge, @PathVariable Long id) {
        return badgeService.update(newBadge, id);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        badgeService.deleteById(id);
    }
}
