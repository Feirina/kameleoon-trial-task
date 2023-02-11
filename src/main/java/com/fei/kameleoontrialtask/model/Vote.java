package com.fei.kameleoontrialtask.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "votes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vote {
    @EmbeddedId
    private UserQuotePK userQuotePK;

    @Enumerated(EnumType.STRING)
    private VoteType type;
}
