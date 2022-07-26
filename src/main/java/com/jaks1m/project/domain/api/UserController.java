package com.jaks1m.project.domain.api;

import com.jaks1m.project.domain.dto.EditUserRequestDto;
import com.jaks1m.project.domain.dto.JoinUserRequestDto;
import com.jaks1m.project.domain.dto.UserDto;
import com.jaks1m.project.domain.dto.UserResponseDto;
import com.jaks1m.project.domain.response.BaseResponse;
import com.jaks1m.project.domain.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping()
    @ApiOperation(value = "사용자 회원가입")
    public ResponseEntity<BaseResponse<UserDto>> join(@RequestBody @Validated JoinUserRequestDto request, HttpServletResponse response) {
        UserDto userDto = userService.join(request, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.<UserDto>builder()
                        .status(200)
                        .body(userDto)
                        .build());
    }

    @DeleteMapping("/me/logout")
    @ApiOperation(value = "사용자 로그아웃")
    @ResponseBody
    public String logout(HttpServletRequest request){
        userService.logoutUser(request);
        return "ok";
    }
    @GetMapping("/me")
    @ApiOperation(value = "사용자 정보조회")
    public ResponseEntity<BaseResponse<UserResponseDto>> findUser(){
        UserResponseDto userResponseDto=userService.findUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.<UserResponseDto>builder()
                        .status(200)
                        .body(userResponseDto)
                        .build());
    }

    @PatchMapping("/me")
    @ApiOperation(value = "사용자 정보수정")
    @ResponseBody
    public String patch(@RequestBody @Validated EditUserRequestDto userRequestDto){
        userService.patchUser(userRequestDto);
        return "ok";
    }

}
