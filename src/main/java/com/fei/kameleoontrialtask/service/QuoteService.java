package com.fei.kameleoontrialtask.service;

import com.fei.kameleoontrialtask.dto.NewQuoteDto;
import com.fei.kameleoontrialtask.dto.QuoteDto;
import com.fei.kameleoontrialtask.dto.UpdateQuoteDto;
import com.fei.kameleoontrialtask.model.VoteType;

import java.util.List;

public interface QuoteService {
    QuoteDto addQuote(NewQuoteDto quoteDto, Long userId);

    QuoteDto getQuote(Long quoteId);

    QuoteDto getRandomQuote();

    QuoteDto updateQuote(Long userId, UpdateQuoteDto quoteDto);

    void deleteQuote(Long quoteId, Long userId);

    QuoteDto voteQuote(Long userId, Long quoteId, VoteType voteType);

    List<QuoteDto> getTopTenQuotes();

    List<QuoteDto> getWorseTenQuotes();
}
