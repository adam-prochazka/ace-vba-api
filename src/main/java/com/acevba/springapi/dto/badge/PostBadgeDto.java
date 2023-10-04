package com.acevba.springapi.api.dto.badge;

import com.acevba.springapi.api.dto.user.UserDto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link com.acevba.springapi.model.Badge}
 */
public class PostBadgeDto implements Serializable {
    private final String level;
    private final String role;
    private final Set<UserDto> users;

    public PostBadgeDto(String level, String role, Set<UserDto> users) {
        this.level = level;
        this.role = role;
        this.users = users;
    }

    public String getLevel() {
        return level;
    }

    public String getRole() {
        return role;
    }

    public Set<UserDto> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostBadgeDto entity = (PostBadgeDto) o;
        return Objects.equals(this.level, entity.level) &&
                Objects.equals(this.role, entity.role) &&
                Objects.equals(this.users, entity.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, role, users);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "level = " + level + ", " +
                "role = " + role + ", " +
                "users = " + users + ")";
    }
}