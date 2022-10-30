package com.codestates.stackoverflow.auth.entity;

import com.codestates.stackoverflow.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
public class RefreshToken extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String id;

    @Column(nullable = false)
    private Long key;

    @Column(nullable = true)

    private String token;

    public RefreshToken updateToken(String token) {
        this.token = token;
        return this;
    }



    public RefreshToken(Long key, String token) {
        this.key = key;

        this.token = token;
    }
}
