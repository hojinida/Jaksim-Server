package com.jaks1m.project.controller.user;

import com.jaks1m.project.dto.user.edit.EditUserDto;
import com.jaks1m.project.dto.user.edit.EditUserPasswordDto;
import com.jaks1m.project.dto.user.edit.FindUserPasswordDto;
import com.jaks1m.project.dto.user.request.JoinUserRequestDto;
import com.jaks1m.project.dto.user.response.UserDto;
import com.jaks1m.project.dto.user.response.UserResponseDto;
import com.jaks1m.project.domain.common.response.BaseResponse;
import com.jaks1m.project.service.user.UserService;
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
        UserDto userDto = userService.joinUser(request);
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
    public ResponseEntity<String> logout(HttpServletRequest request){
        userService.logoutUser(request);
        return ResponseEntity.status(200).body("사용자 로그아웃 성공");
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
    public ResponseEntity<String> patch(@RequestBody @Validated EditUserDto request){
        userService.patchUser(request);
        return ResponseEntity.status(200).body("사용자 정보수정 성공");
    }

    @PatchMapping("/me/quit")
    @ApiOperation(value = "사용자 회원탈퇴")
    @ResponseBody
    public ResponseEntity<String> quitUser(HttpServletRequest request){
        userService.quitUser(request);
        return ResponseEntity.status(200).body("사용자 회원탈퇴 성공");
    }

    @PatchMapping("/password")
    @ApiOperation(value = "사용자 비밀번호 찾기")
    @ResponseBody
    public ResponseEntity<String> findPassword(FindUserPasswordDto request){
        userService.findPassword(request);
        return ResponseEntity.status(200).body("사용자 비밀번호 찾기 성공");
    }

    @PatchMapping("/me/password")
    @ApiOperation(value = "사용자 비밀번호 변경")
    @ResponseBody
    public ResponseEntity<String> editPassword(@RequestBody @Validated EditUserPasswordDto request){
        userService.editPassword(request);
        return ResponseEntity.status(200).body("사용자 비밀번호 변경 성공");
    }


}
