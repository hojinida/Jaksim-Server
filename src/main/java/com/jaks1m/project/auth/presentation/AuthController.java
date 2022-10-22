package com.jaks1m.project.auth.presentation;

import com.jaks1m.project.auth.presentation.dto.UserLoginRequest;
import com.jaks1m.project.user.application.dto.UserCreateResponse;
import com.jaks1m.project.common.domain.BaseResponse;
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
    public ResponseEntity<BaseResponse<UserCreateResponse>> login(@RequestBody @Validated UserLoginRequest request){
        UserCreateResponse userCreateResponse =authService.login(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.<UserCreateResponse>builder()
                        .status(200)
                        .body(userCreateResponse)
                        .build());
    }
    @PostMapping("/reissue")
    @ApiOperation(value = "토큰 재발급")
    public ResponseEntity<BaseResponse<UserCreateResponse>> reissue(HttpServletRequest request) {
        UserCreateResponse userCreateResponse = authService.reissue(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.<UserCreateResponse>builder()
                        .status(200)
                        .body(userCreateResponse)
                        .build());
    }
}
