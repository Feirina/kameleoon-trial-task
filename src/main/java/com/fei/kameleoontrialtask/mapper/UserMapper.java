package com.fei.kameleoontrialtask.mapper;

import com.fei.kameleoontrialtask.dto.NewUserDto;
import com.fei.kameleoontrialtask.dto.UserDto;
import com.fei.kameleoontrialtask.model.User;

public class UserMapper {
    public static User toUser(NewUserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .dateOfCreation(user.getDateOfCreation())
                .build();
    }
}
