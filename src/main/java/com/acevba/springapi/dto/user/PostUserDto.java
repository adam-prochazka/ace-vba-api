package com.acevba.springapi.api.dto.user;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.acevba.springapi.model.User}
 */
public class PostUserDto implements Serializable {
    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public PostUserDto() {}

    public PostUserDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostUserDto entity = (PostUserDto) o;
        return Objects.equals(this.username, entity.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "username = " + username + ")";
    }
}