package com.codestates.stackoverflow.auth.entity;

import com.codestates.stackoverflow.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "refresh_token")
@Getter
@NoArgsConstructor
public class RefreshToken extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenId;
    @Column(nullable = false)
    private Long tokenKey;
    @Column(nullable = false)
    private String token;

    public RefreshToken updateToken(String token) {
        this.token = token;
        return this;
    }

    public RefreshToken(Long tokenKey, String token) {
        this.tokenKey = tokenKey;
        this.token = token;
    }

}
