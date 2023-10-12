package com.acevba.springapi.model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "username", nullable = false, unique = false)
    private String username;
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            }
    )
    private Set<Badge> badges = new LinkedHashSet<>();
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            }
    )
    @JoinTable(
            name = "user_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> events = new LinkedHashSet<>();

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public User() {}
    public User(String username) {
        this.username = username;
    }

    public Set<Badge> getBadges() {
        return badges;
    }

    public void setBadges(Set<Badge> badges) {
        this.badges = badges;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void addBadge(Badge badge) {
        badges.add(badge);
        badge.getUsers().add(this);
    }

    public void addEvent(Event event) {
        events.add(event);
        event.getUsers().add(this);
    }

    public void removeBadge(Badge badge) {
        badges.remove(badge);
    }

    public void removeAllBadges() {
        badges.clear();
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    public void removeAllEvents() {
        events.clear();
    }
}