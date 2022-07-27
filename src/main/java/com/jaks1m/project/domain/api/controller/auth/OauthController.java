package com.jaks1m.project.domain.api.controller.auth;

import com.jaks1m.project.domain.api.dto.request.LoginUserRequestDto;
import com.jaks1m.project.domain.api.dto.reponse.UserDto;
import com.jaks1m.project.domain.common.BaseResponse;
import com.jaks1m.project.domain.api.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class OauthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "사용자 로그인")
    public ResponseEntity<BaseResponse<UserDto>> login(@RequestBody @Validated LoginUserRequestDto request, HttpServletResponse response) throws Exception {
        UserDto userDto=authService.login(request,response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.<UserDto>builder()
                        .status(200)
                        .body(userDto)
                        .build());
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "토큰 재발급")
    public ResponseEntity<BaseResponse<UserDto>> reissue(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDto userDto = authService.reissue(request, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.<UserDto>builder()
                        .status(200)
                        .body(userDto)
                        .build());
    }

}
