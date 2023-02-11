package com.fei.kameleoontrialtask.controller;

import com.fei.kameleoontrialtask.dto.NewUserDto;
import com.fei.kameleoontrialtask.dto.UserDto;
import com.fei.kameleoontrialtask.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto createUser(@RequestBody @Valid NewUserDto userDto) {
        log.info("create new user");
        return userService.createUser(userDto);
    }
}
