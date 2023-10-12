package com.acevba.springapi.controller;

import com.acevba.springapi.exception.ResourceNotFoundException;
import com.acevba.springapi.model.Badge;
import com.acevba.springapi.model.User;
import com.acevba.springapi.repository.BadgeRepository;
import com.acevba.springapi.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@Tag(name = "Badge", description = "Badge management APIs")
public class BadgeController {

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Get all badges", tags = { "badges", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Badge.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/badges")
    public ResponseEntity<List<Badge>> getAllBadges() {
        List<Badge> badges = new ArrayList<>();
        badges.addAll(badgeRepository.findAll());
        return new ResponseEntity<>(badges, HttpStatus.OK);
    }

    @Operation(summary = "Get a badge by ID", tags = { "badges", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Badge.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/badges/{id}")
    public ResponseEntity<Badge> getBadgeById(@PathVariable Long id) {
        return new ResponseEntity<>(badgeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Badge with id = " + id)), HttpStatus.OK);
    }

    @Operation(summary = "Create a new badge", tags = { "badges", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = Badge.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/badges")
    public ResponseEntity<Badge> createBadge(@RequestBody Badge badge) {
        return new ResponseEntity<>(badgeRepository.save(badge), HttpStatus.CREATED);
    }

    /**
     * Preserve id
     */
    @Operation(summary = "Update a badge", tags = { "badges", "put" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Badge.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PutMapping("/badges/{id}")
    public ResponseEntity<Badge> updateBadge(@RequestBody Badge badge, @PathVariable Long id) {
        Badge _badge = badgeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Badge with id = " + id));
        _badge.setLevel(badge.getLevel());
        _badge.setRole(badge.getRole());
        _badge.setUsers(badge.getUsers());
        return new ResponseEntity<>(badgeRepository.save(_badge), HttpStatus.OK);
    }

    @Operation(summary = "Delete a badge", tags = { "badges", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/badges/{id}")
    public ResponseEntity<HttpStatus> deleteBadge(@PathVariable Long id) {
        badgeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete all badges", tags = { "badges", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/badges")
    public ResponseEntity<HttpStatus> deleteAllBadges() {
        badgeRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Get all badges by user ID", tags = { "badges", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Set.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/users/{userId}/badges")
    public ResponseEntity<Set<Badge>> getAllBadgesByUserId(
            @PathVariable(value = "userId") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id = " + userId));
        return new ResponseEntity<>(user.getBadges(), HttpStatus.OK);
    }

    @Operation(summary = "Get all users by badge ID", tags = { "badges", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Set.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/badges/{badgeId}/users")
    public ResponseEntity<Set<User>> getAllUsersByBadgeId(
            @PathVariable(value = "badgeId") Long badgeId) {
        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Badge with id = " + badgeId));

        return new ResponseEntity<>(badge.getUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Add a badge to a user", tags = { "badges", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = Badge.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
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

    @Operation(summary = "Delete a badge from a user", tags = { "badges", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/users/{userId}/badges/{badgeId}")
    public ResponseEntity<HttpStatus> deleteBadgeFromUser(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "badgeId") Long badgeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with id = " + userId));
        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Badge with id = " + badgeId));

        user.removeBadge(badge);
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete all badges from a user", tags = { "badges", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/users/{userId}/badges")
    public ResponseEntity<HttpStatus> deleteAllBadgesFromUser(
            @PathVariable(value = "userId") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with id = " + userId));

        user.removeAllBadges();
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete all users from a badge", tags = { "badges", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/badges/{badgeId}/users")
    public ResponseEntity<HttpStatus> deleteAllUsersFromBadge(
            @PathVariable(value = "badgeId") Long badgeId) {

        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Badge with id = " + badgeId));

        badge.removeAllUsers();
        badgeRepository.save(badge);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}