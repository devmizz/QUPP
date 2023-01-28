package com.qupp.mail.dto;

import com.qupp.user.repository.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EmailToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String verificationToken;
    @Column
    private LocalDateTime verificationTokenTime;
    @Column
    private boolean expired;
    private LocalDateTime expirationDate;
    private long userId;

    @Builder
    public EmailToken(long id, long userId, String verificationToken, LocalDateTime verificationTokenTime, LocalDateTime expirationDate) {
        this.id = id;
        this.userId = userId;
        this.verificationToken = verificationToken;
        this.verificationTokenTime = verificationTokenTime;
        this.expirationDate = expirationDate;
        this.expired = false;
    }

    public static EmailToken generateVerificationToken(User user) {
        EmailToken emailToken = EmailToken.builder()
                .userId(user.getId())
                .verificationToken(UUID.randomUUID().toString())
                .verificationTokenTime(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusMinutes(5L))
                .build();
        return emailToken;
    }

    public void expiredToken() {
        this.expired = true;
    }
}
