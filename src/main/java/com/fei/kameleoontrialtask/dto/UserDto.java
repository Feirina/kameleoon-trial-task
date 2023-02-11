package com.fei.kameleoontrialtask.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserDto {
    private String name;

    private String email;

    private LocalDate dateOfCreation;
}
