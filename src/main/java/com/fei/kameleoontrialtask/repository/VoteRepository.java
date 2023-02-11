package com.fei.kameleoontrialtask.repository;

import com.fei.kameleoontrialtask.model.UserQuotePK;
import com.fei.kameleoontrialtask.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, UserQuotePK> {
}
