package com.acevba.springapi.controller;

import com.acevba.springapi.exception.ResourceNotFoundException;
import com.acevba.springapi.model.Event;
import com.acevba.springapi.model.User;
import com.acevba.springapi.repository.EventRepository;
import com.acevba.springapi.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@Tag(name = "Event", description = "Event management APIs")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Get all events")
            @ApiResponse(responseCode = "200", description = "List of all events", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = Event.class)), mediaType = "application/json") })
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = new ArrayList<>();
        events.addAll(eventRepository.findAll());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @Operation(summary = "Get event details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event details", content = {
                    @Content(schema = @Schema(implementation = Event.class), mediaType = "application/json") })})
    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return new ResponseEntity<>(eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id = " + id)), HttpStatus.OK);
    }

    @Operation(summary = "Create a new event")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created event", content = {
                    @Content(schema = @Schema(implementation = Event.class), mediaType = "application/json") })})
    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return new ResponseEntity<>(eventRepository.save(event), HttpStatus.CREATED);
    }

    /**
     * Preserve id
     */
    @Operation(summary = "Update an event")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated event", content = {
                    @Content(schema = @Schema(implementation = Event.class), mediaType = "application/json") })})
    @PutMapping("/events/{id}")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event, @PathVariable Long id) {
        Event _event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id = " + id));
        _event.setName(event.getName());
        _event.setDate(event.getDate());
        _event.setUsers(event.getUsers());
        return new ResponseEntity<>(eventRepository.save(_event), HttpStatus.OK);
    }

    @Operation(summary = "Delete an event")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted successfully", content = { @Content })})
    @DeleteMapping("/events/{id}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable Long id) {
        eventRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete all events")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted successfully", content = { @Content })})
    @DeleteMapping("/events")
    public ResponseEntity<HttpStatus> deleteAllEvents() {
        eventRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Get all events by user ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of user events", content = {
                    @Content(array = @ArraySchema( schema = @Schema(implementation = Event.class)), mediaType = "application/json") })})
    @GetMapping("/users/{userId}/events")
    public ResponseEntity<Set<Event>> getAllEventsByUserId(
            @PathVariable(value = "userId") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id = " + userId));
        return new ResponseEntity<>(user.getEvents(), HttpStatus.OK);
    }

    @Operation(summary = "Get all users by event ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of event users", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)), mediaType = "application/json") })})
    @GetMapping("/events/{eventId}/users")
    public ResponseEntity<Set<User>> getAllUsersByEventId(
            @PathVariable(value = "eventId") Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Event with id = " + eventId));

        return new ResponseEntity<>(event.getUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Add an event to a user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Event added", content = {
                    @Content(schema = @Schema(implementation = Event.class), mediaType = "application/json") })})
    @PostMapping("/users/{userId}/events")
    public ResponseEntity<Event> addEvent(
            @PathVariable(value = "userId") Long userId,
            @RequestBody Event eventReq) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id = " + userId));

        Event event;

        if (eventReq.getId() == null)
            event = eventRepository.save(eventReq);

        else
            event = eventRepository.findById(eventReq.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Event with id = " + eventReq.getId()));

        user.addEvent(event);
        userRepository.save(user);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @Operation(summary = "Remove user from event")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Removed successfully", content = { @Content })})
    @DeleteMapping("/users/{userId}/events/{eventId}")
    public ResponseEntity<HttpStatus> deleteEventFromUser(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "eventId") Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with id = " + userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Event with id = " + eventId));

        user.removeEvent(event);
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Remove user from all events")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Removed successfully", content = { @Content(schema = @Schema()) })})
    @DeleteMapping("/users/{userId}/events")
    public ResponseEntity<HttpStatus> deleteAllEventsFromUser(
            @PathVariable(value = "userId") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with id = " + userId));

        user.removeAllEvents();
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete all users from an event")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Removed successfully", content = { @Content(schema = @Schema()) })})
    @DeleteMapping("/events/{eventId}/users")
    public ResponseEntity<HttpStatus> deleteAllUsersFromEvent(
            @PathVariable(value = "eventId") Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Event with id = " + eventId));

        event.removeAllUsers();
        eventRepository.save(event);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}