package com.acevba.springapi.api.dto.convertor;

import com.acevba.springapi.api.dto.user.PostUserDto;
import com.acevba.springapi.api.dto.user.UserDto;
import com.acevba.springapi.model.User;

public class UserConvertor {
    public static User userFromUserDto(UserDto userDto) {
        return new User(userDto.getUsername());
    }

    public static User userFromSimpleDto(PostUserDto postUserDto) {
        return new User(postUserDto.getUsername());
    }

    public static UserDto DtoFromUser(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getBadges());
    }
}
