package com.codestates.stackoverflow.question.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
@Access(AccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
public class ActiveInfo {
    @Column(name = "writerId")
    Long memberId;

    @Column
    LocalDateTime lastActiveAt;

    @Enumerated(EnumType.STRING)
    @Column
    ActiveType activeType;
}
