package com.fei.kameleoontrialtask.controller;

import com.fei.kameleoontrialtask.dto.NewQuoteDto;
import com.fei.kameleoontrialtask.dto.QuoteDto;
import com.fei.kameleoontrialtask.dto.UpdateQuoteDto;
import com.fei.kameleoontrialtask.model.VoteType;
import com.fei.kameleoontrialtask.service.QuoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/quotes")
@Slf4j
public class QuoteController {
    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping("/{userId}")
    public QuoteDto addQuote(@RequestBody @Valid NewQuoteDto quoteDto, @PathVariable Long userId) {
        log.info("add new quote by user with id {}", userId);
        return quoteService.addQuote(quoteDto, userId);
    }

    @GetMapping("/{quoteId}")
    public QuoteDto getQuote(@PathVariable Long quoteId) {
        log.info("get quote with id {}", quoteId);
        return quoteService.getQuote(quoteId);
    }

    @GetMapping("/random")
    public QuoteDto getRandomQuote() {
        log.info("get random quote");
        return quoteService.getRandomQuote();
    }

    @PatchMapping("/{userId}")
    public QuoteDto updateQuote(@PathVariable Long userId, @RequestBody UpdateQuoteDto quoteDto) {
        log.info("update quote with id {} by user {}", quoteDto.getId(), userId);
        return quoteService.updateQuote(userId, quoteDto);
    }

    @DeleteMapping("/{quoteId}/{userId}")
    public void deleteQuote(@PathVariable Long quoteId, @PathVariable Long userId) {
        log.info("delete quote with id {} by user {}", quoteId, userId);
        quoteService.deleteQuote(quoteId, userId);
    }

    @PatchMapping("/{quoteId}/{userId}")
    public QuoteDto voteQuote(@PathVariable Long quoteId,
                              @PathVariable Long userId,
                              @RequestParam String voteType) {
        log.info("add {} to quote {}", voteType, quoteId);
        return quoteService.voteQuote(userId, quoteId, VoteType.valueOf(voteType));
    }

    @GetMapping("/top")
    public List<QuoteDto> getTopTenQuotes() {
        log.info("get ten top quotes");
        return quoteService.getTopTenQuotes();
    }

    @GetMapping("/worse")
    public List<QuoteDto> getWorseTenQuote() {
        log.info("get worse ten quotes");
        return quoteService.getWorseTenQuotes();
    }
}
