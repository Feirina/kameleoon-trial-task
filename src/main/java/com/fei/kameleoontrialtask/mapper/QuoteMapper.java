package com.fei.kameleoontrialtask.mapper;

import com.fei.kameleoontrialtask.dto.NewQuoteDto;
import com.fei.kameleoontrialtask.dto.QuoteDto;
import com.fei.kameleoontrialtask.model.Quote;

public class QuoteMapper {
    public static Quote toQuote(NewQuoteDto quoteDto) {
        return Quote.builder()
                .content(quoteDto.getContent())
                .build();
    }

    public static QuoteDto toQuoteDto(Quote quote) {
        return QuoteDto.builder()
                .content(quote.getContent())
                .dateOfCreation(quote.getDateOfCreate())
                .score(quote.getScore())
                .build();
    }
}
