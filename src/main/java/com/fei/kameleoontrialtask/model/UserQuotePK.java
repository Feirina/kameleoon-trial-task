package com.fei.kameleoontrialtask.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserQuotePK implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "quote_id")
    private Long quoteId;
}
