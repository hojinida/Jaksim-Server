package com.jaks1m.project.auth.presentation;

import com.jaks1m.project.auth.presentation.dto.UserLoginRequest;
import com.jaks1m.project.user.application.dto.UserCreateResponse;
import com.jaks1m.project.auth.application.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "사용자 로그인")
    public ResponseEntity<UserCreateResponse> login(@RequestBody @Validated UserLoginRequest request){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.login(request));
    }
    @PostMapping("/reissue")
    @ApiOperation(value = "토큰 재발급")
    public ResponseEntity<UserCreateResponse> reissue(HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.reissue(request));
    }
}
