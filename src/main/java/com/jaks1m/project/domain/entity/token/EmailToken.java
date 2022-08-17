package com.jaks1m.project.domain.entity.token;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "EMAIL_TOKEN_SEQ_GENERATOR", sequenceName = "EMAIL_TOKEN_SEQ")
public class EmailToken {
    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;
    private static final int EMAIL_TOKEN_LENGTH=12;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "EMAIL_TOKEN_SEQ_GENERATOR")
    private Long id;

    private String token;

    private LocalDateTime expirationDate;

    // 이메일 인증 토큰 생성
    public static EmailToken createEmailToken() {
        return EmailToken.builder()
                .token(RandomStringUtils.randomAlphanumeric(EMAIL_TOKEN_LENGTH))
                .expirationDate(LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE))
                .build();
    }

    @Builder
    public EmailToken(Long id, String token, LocalDateTime expirationDate) {
        this.id = id;
        this.token = token;
        this.expirationDate = expirationDate;
    }
}
