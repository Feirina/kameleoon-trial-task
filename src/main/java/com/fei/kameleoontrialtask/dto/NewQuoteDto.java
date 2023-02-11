package com.fei.kameleoontrialtask.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class NewQuoteDto {
    @NotBlank
    private String content;
}
