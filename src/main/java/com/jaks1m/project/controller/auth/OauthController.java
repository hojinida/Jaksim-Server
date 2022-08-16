package com.jaks1m.project.controller.auth;

import com.jaks1m.project.dto.user.request.LoginUserRequestDto;
import com.jaks1m.project.dto.user.response.UserDto;
import com.jaks1m.project.domain.common.response.BaseResponse;
import com.jaks1m.project.service.auth.AuthService;
import com.jaks1m.project.domain.entity.token.RefreshToken;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class OauthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "사용자 로그인")
    public ResponseEntity<BaseResponse<UserDto>> login(@RequestBody @Validated LoginUserRequestDto request, HttpServletResponse response) throws Exception {
        UserDto userDto=authService.login(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.<UserDto>builder()
                        .status(200)
                        .body(userDto)
                        .build());
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "토큰 재발급")
    public ResponseEntity<BaseResponse<UserDto>> reissue(HttpServletRequest request) {
        UserDto userDto = authService.reissue(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.<UserDto>builder()
                        .status(200)
                        .body(userDto)
                        .build());
    }

    @GetMapping("/me")
    @ApiOperation(value = "로그인 성공")
    public ResponseEntity<BaseResponse<UserDto>> loginSuccess(@RequestParam String accessToken,@RequestParam String refreshToken){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.<UserDto>builder()
                        .status(200)
                        .body(UserDto.builder()
                                .accessToken(accessToken)
                                .refreshToken(RefreshToken.builder().value(refreshToken).build())
                                .build())
                        .build());
    }
}
