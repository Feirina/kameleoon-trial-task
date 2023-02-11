package com.fei.kameleoontrialtask.repository;

import com.fei.kameleoontrialtask.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    Optional<Quote> findByIdAndCreatorId(Long id, Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Modifying
    @Query("UPDATE Quote q " +
            " SET q.score = q.score + 1 " +
            " WHERE q.id = :quoteId")
    void incrementScore(Long quoteId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Modifying
    @Query("UPDATE Quote q " +
            " SET q.score = q.score - 1 " +
            " WHERE q.id = :quoteId")
    void decrementScore(Long quoteId);

    List<Quote> findTop10ByOrderByScoreDesc();

    List<Quote> findTop10ByOrderByScore();
}
