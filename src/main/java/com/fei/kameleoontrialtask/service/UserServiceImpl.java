package com.fei.kameleoontrialtask.service;

import com.fei.kameleoontrialtask.dto.NewUserDto;
import com.fei.kameleoontrialtask.dto.UserDto;
import com.fei.kameleoontrialtask.mapper.UserMapper;
import com.fei.kameleoontrialtask.model.User;
import com.fei.kameleoontrialtask.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(NewUserDto userDto) {
        User user = UserMapper.toUser(userDto);
        user.setDateOfCreation(LocalDate.now());
        return UserMapper.toUserDto(userRepository.save(user));
    }
}
