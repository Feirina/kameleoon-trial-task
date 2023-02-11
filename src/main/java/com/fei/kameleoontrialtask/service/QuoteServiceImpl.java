package com.fei.kameleoontrialtask.service;

import com.fei.kameleoontrialtask.dto.NewQuoteDto;
import com.fei.kameleoontrialtask.dto.QuoteDto;
import com.fei.kameleoontrialtask.dto.UpdateQuoteDto;
import com.fei.kameleoontrialtask.exception.BadRequestException;
import com.fei.kameleoontrialtask.exception.ConflictException;
import com.fei.kameleoontrialtask.exception.NotFoundException;
import com.fei.kameleoontrialtask.mapper.QuoteMapper;
import com.fei.kameleoontrialtask.model.*;
import com.fei.kameleoontrialtask.repository.QuoteRepository;
import com.fei.kameleoontrialtask.repository.UserRepository;
import com.fei.kameleoontrialtask.repository.VoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuoteServiceImpl implements QuoteService{
    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    public QuoteServiceImpl(QuoteRepository quoteRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.quoteRepository = quoteRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public QuoteDto addQuote(NewQuoteDto quoteDto, Long userId) {
        Quote quote = QuoteMapper.toQuote(quoteDto);
        quote.setDateOfCreate(LocalDate.now());
        quote.setCreator(getAndCheckUser(userId));
        return QuoteMapper.toQuoteDto(quoteRepository.save(quote));
    }

    @Override
    @Transactional(readOnly = true)
    public QuoteDto getQuote(Long quoteId) {
        return QuoteMapper.toQuoteDto(getAndCheckQuote(quoteId));
    }

    @Override
    @Transactional(readOnly = true)
    public QuoteDto getRandomQuote() {
        int qty = (int) quoteRepository.count();
        Random random = new Random();
        int idx = random.nextInt(qty);
        Page<Quote> quotePage = quoteRepository.findAll(PageRequest.of(idx, 1));
        Quote quote = null;
        if (quotePage.hasContent()) {
            quote = quotePage.getContent().get(0);
        }
        return quote==null ? null : QuoteMapper.toQuoteDto(quote);
    }

    @Override
    public QuoteDto updateQuote(Long userId, UpdateQuoteDto quoteDto) {
        Quote quote = getQuoteByUserIdAndQuoteId(quoteDto.getId(), userId, "only creator can modify quote");
        quote.setContent(quoteDto.getContent());
        return QuoteMapper.toQuoteDto(quoteRepository.save(quote));
    }

    @Override
    public void deleteQuote(Long quoteId, Long userId) {
        Quote quote = getQuoteByUserIdAndQuoteId(quoteId, userId, "only creator can delete quote");
        quoteRepository.delete(quote);
    }

    @Override
    public QuoteDto voteQuote(Long userId, Long quoteId, VoteType voteType) {
        getAndCheckQuote(quoteId);
        getAndCheckUser(userId);
        Optional<Vote> vote = voteRepository.findById(new UserQuotePK(userId, quoteId));
        if (vote.isPresent()) {
            if (vote.get().getType().equals(voteType)) {
                throw new ConflictException("you already set " + voteType.toString() + " to this quote");
            } else {
                Vote saveVote = vote.get();
                saveVote.setType(voteType);
                voteRepository.save(saveVote);
                if (voteType.equals(VoteType.LIKE)) {
                    quoteRepository.incrementScore(quoteId);
                } else {
                    quoteRepository.decrementScore(quoteId);
                }
            }
        } else {
            if (voteType.equals(VoteType.LIKE)) {
                upVote(userId, quoteId);
            } else {
                downVote(userId, quoteId);
            }
        }
        Quote quote = quoteRepository.findById(quoteId).get();
        return QuoteMapper.toQuoteDto(quote);
    }

    @Override
    public List<QuoteDto> getTopTenQuotes() {
        List<Quote> quotes = quoteRepository.findTop10ByOrderByScoreDesc();
        return quotes.stream()
                .map(QuoteMapper :: toQuoteDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuoteDto> getWorseTenQuotes() {
        List<Quote> quotes = quoteRepository.findTop10ByOrderByScore();
        return quotes.stream()
                .map(QuoteMapper :: toQuoteDto)
                .collect(Collectors.toList());
    }

    private void upVote(Long userId, Long quoteId) {
        voteRepository.save(new Vote(new UserQuotePK(userId, quoteId), VoteType.LIKE));
        quoteRepository.incrementScore(quoteId);
    }

    private void downVote(Long userId, Long quoteId) {
        voteRepository.save(new Vote(new UserQuotePK(userId, quoteId), VoteType.DISLIKE));
        quoteRepository.decrementScore(quoteId);
    }

    private User getAndCheckUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user with id " + id + " not found"));
    }

    private Quote getAndCheckQuote(Long id) {
        return quoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("quote with id " + id + "not found"));
    }

    private Quote getQuoteByUserIdAndQuoteId(Long quoteId, Long userId, String message) {
        return quoteRepository.findByIdAndCreatorId(quoteId, userId)
                .orElseThrow(() -> new BadRequestException(message));
    }
}
