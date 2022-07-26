package com.jaks1m.project.domain.oauth.token;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "EMAIL_SEQ_GENERATOR", sequenceName = "EMAIL_SEQ")
public class EmailToken {
    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;
    private static final int EMAIL_TOKEN_LENGTH=6;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "EMAIL_SEQ_GENERATOR")
    private Long id;

    private String emailToken;

    private LocalDateTime expirationDate;

    private boolean expired;

    // 이메일 인증 토큰 생성
    public static EmailToken createEmailToken() {
        return EmailToken.builder()
                .emailToken(RandomStringUtils.randomAlphanumeric(EMAIL_TOKEN_LENGTH))
                .expirationDate(LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE))
                .expired(false)
                .build();
    }
    // 토큰 만료
    public void setTokenToUsed() {
        this.expired = true;
    }

    @Builder
    public EmailToken(Long id, String emailToken, LocalDateTime expirationDate, boolean expired) {
        this.id = id;
        this.emailToken = emailToken;
        this.expirationDate = expirationDate;
        this.expired = expired;
    }
}
