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
    private String refreshTokenId;
    @Column(nullable = false)
    private Long key;
    @Column(nullable = false)
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
