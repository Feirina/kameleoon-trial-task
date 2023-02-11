package com.fei.kameleoontrialtask.service;

import com.fei.kameleoontrialtask.dto.NewUserDto;
import com.fei.kameleoontrialtask.dto.UserDto;

public interface UserService {
    UserDto createUser(NewUserDto userDto);
}
