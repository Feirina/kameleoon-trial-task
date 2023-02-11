package com.fei.kameleoontrialtask.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class QuoteDto {
    private String content;

    private LocalDate dateOfCreation;

    private Long score;
}
