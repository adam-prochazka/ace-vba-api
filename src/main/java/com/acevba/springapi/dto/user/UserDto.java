package com.acevba.springapi.dto.user;

import com.acevba.springapi.model.Badge;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link com.acevba.springapi.model.User}
 */
public class UserDto implements Serializable {
    private final Long id;
    private final String username;
    private final Set<Badge> badges;

    public UserDto(Long id, String username,Set<Badge> badges) {
        this.id = id;
        this.username = username;
        this.badges = badges;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto entity = (UserDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.username, entity.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "username = " + username + ")";
    }

    public Set<Badge> getBadges() {
        return badges;
    }
}