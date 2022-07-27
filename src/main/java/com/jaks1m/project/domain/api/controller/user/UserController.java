package com.jaks1m.project.domain.api.controller.user;

import com.jaks1m.project.domain.api.dto.edit.EditUserDto;
import com.jaks1m.project.domain.api.dto.edit.EditUserPasswordDto;
import com.jaks1m.project.domain.api.dto.request.JoinUserRequestDto;
import com.jaks1m.project.domain.api.dto.reponse.UserDto;
import com.jaks1m.project.domain.api.dto.reponse.UserResponseDto;
import com.jaks1m.project.domain.common.BaseResponse;
import com.jaks1m.project.domain.api.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping()
    @ApiOperation(value = "사용자 회원가입")
    public ResponseEntity<BaseResponse<UserDto>> join(@RequestBody @Validated JoinUserRequestDto request) {
        UserDto userDto = userService.join(request);
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
    public String patch(@RequestBody @Validated EditUserDto request){
        userService.patchUser(request);
        return "ok";
    }

    @PatchMapping("/me/quit")
    @ApiOperation(value = "사용자 회원탈퇴")
    @ResponseBody
    public String quit(HttpServletRequest request){
        userService.quit(request);
        return "ok";
    }

    @GetMapping("/me/password")
    @ApiOperation(value = "사용자 비밀번호 변경")
    @ResponseBody
    public Boolean editPassword(@RequestBody @Validated EditUserPasswordDto request){
        userService.editPassword(request);
        return true;
    }
}
