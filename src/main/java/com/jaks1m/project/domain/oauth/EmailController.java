package com.jaks1m.project.domain.oauth;

import com.jaks1m.project.domain.dto.UserEmailRequestDto;
import com.jaks1m.project.domain.service.EmailService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class EmailController {
    private final EmailService emailService;

    @ApiOperation(value = "이메일 인증 발송")
    @PostMapping("/email")
    @ResponseBody
    public String sendEmail(@RequestBody @Validated UserEmailRequestDto request){
        emailService.createEmailToken(request.getEmail());
        return "ok";
    }

    @ApiOperation(value = "이메일 인증 검증")
    @GetMapping("/confirm-email")
    public String authEmail(@RequestParam @Validated String token) {
        emailService.verifyEmail(token);
        return "ok";
    }
}
