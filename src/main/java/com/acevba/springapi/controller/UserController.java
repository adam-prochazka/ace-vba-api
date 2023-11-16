package com.acevba.springapi.controller;

import com.acevba.springapi.exception.ResourceNotFoundException;
import com.acevba.springapi.model.User;
import com.acevba.springapi.repository.UserRepository;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "User", description = "User management APIs")
public class UserController {

    @Autowired private UserRepository userRepository;

    @Operation(summary = "Get all users")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "List of users",
                content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = User.class)),
                            mediaType = "application/json")
                })
    })
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam Optional<String> username) {
        List<User> users = new ArrayList<>();

        if (username.isPresent()) users.addAll(userRepository.findByUsername(username.get()));
        else users.addAll(userRepository.findAll());

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Get a user detail")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "User detail",
                content = {
                    @Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = "application/json")
                })
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User with id = " + id)),
                HttpStatus.OK);
    }

    @Operation(summary = "Create a new user")
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Created user",
                content = {
                    @Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = "application/json")
                })
    })
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a user")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Updated user",
                content = {
                    @Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = "application/json")
                })
    })
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) {
        User _user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User with id = " + id));
        _user.setUsername(user.getUsername());
        _user.setBadges(user.getBadges());
        _user.setEvents(user.getEvents());
        return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
    }

    @Operation(summary = "Delete a user")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Deleted successfully",
                content = {@Content(schema = @Schema())})
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete all users")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Deleted successfully",
                content = {@Content(schema = @Schema())})
    })
    @DeleteMapping("/users")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        userRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
