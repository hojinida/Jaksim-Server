package com.jaks1m.project.controller.auth;

import com.jaks1m.project.dto.user.request.EmailTokenRequestDto;
import com.jaks1m.project.dto.user.request.UserEmailRequestDto;
import com.jaks1m.project.service.auth.EmailService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/email")
public class EmailController {
    private final EmailService emailService;

    @ApiOperation(value = "이메일 인증 발송")
    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody @Validated UserEmailRequestDto request){
        emailService.createEmailToken(request.getEmail());
        return ResponseEntity.status(200).body("이메일 발송 성공");
    }

    @ApiOperation(value = "이메일 인증 검증")
    @PostMapping("/auth")
    public ResponseEntity<String> authEmail(@RequestBody @Validated EmailTokenRequestDto request) {
        emailService.verifyEmail(request);
        return ResponseEntity.status(200).body("이메일 인증 성공");
    }
}
