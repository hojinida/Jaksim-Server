package com.jaks1m.project.service.auth;

import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import com.jaks1m.project.domain.entity.token.EmailToken;
import com.jaks1m.project.repository.auth.EmailTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailService {
    private final EmailTokenRepository emailTokenRepository;
    private final EmailSenderService emailSenderService;
    @Transactional
    public void verifyEmail(String token) throws CustomException {
        deleteExpiredToken();
        Optional<EmailToken> emailToken = emailTokenRepository
                .findByEmailTokenAndExpirationDateAfter(token, LocalDateTime.now());

        // 토큰이 없다면 예외 발생
        emailToken.orElseThrow(() -> new CustomException(ErrorCode.EMAIL_TOKEN_NOT_FOUND));
        emailTokenRepository.delete(emailToken.get());
    }

    @Transactional
    public void createEmailToken(String receiverEmail) {
        // 이메일 토큰 저장
        EmailToken emailToken = EmailToken.createEmailToken();
        emailTokenRepository.save(emailToken);

        // 이메일 전송
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("https://jaks1m.shop/auth/confirm-email?token="+emailToken.getEmailToken());
        emailSenderService.sendEmail(mailMessage);
    }

    @Transactional
    public void deleteExpiredToken(){
        List<EmailToken> emailTokens = emailTokenRepository.findAllByExpirationDateAfter(LocalDateTime.now());
        emailTokenRepository.deleteAll(emailTokens);
    }

}
