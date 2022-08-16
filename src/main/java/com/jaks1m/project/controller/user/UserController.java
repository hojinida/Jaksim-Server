package com.jaks1m.project.controller.user;

import com.jaks1m.project.dto.community.response.ImageDto;
import com.jaks1m.project.dto.user.edit.EditUserDto;
import com.jaks1m.project.dto.user.edit.EditUserPasswordDto;
import com.jaks1m.project.dto.user.edit.FindUserPasswordDto;
import com.jaks1m.project.dto.user.request.JoinUserRequestDto;
import com.jaks1m.project.dto.user.response.UserDto;
import com.jaks1m.project.dto.user.response.UserResponseDto;
import com.jaks1m.project.domain.common.response.BaseResponse;
import com.jaks1m.project.service.aws.AwsS3Service;
import com.jaks1m.project.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AwsS3Service awsS3Service;
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
    public ResponseEntity<String> editUser(@RequestBody @Validated EditUserDto request){
        userService.patchUser(request);
        return ResponseEntity.status(200).body("사용자 정보수정 성공");
    }

    @PostMapping("/me/image")
    @ApiOperation(value = "사용자 프로필 등록")
    public ResponseEntity<String> deleteImage(@RequestPart("file") List<MultipartFile> multipartFiles) throws IOException {
        List<ImageDto> images=awsS3Service.upload(multipartFiles,"upload");
        userService.addImage(images);
        return ResponseEntity.status(200).body("사용자 프로필 등록 성공");
    }

    @DeleteMapping("/me/image")
    @ApiOperation(value = "사용자 프로필 삭제")
    public ResponseEntity<String> deleteImage(){
        userService.deleteImage();
        return ResponseEntity.status(200).body("사용자 프로필 삭제 성공");
    }

    @PatchMapping("/me/quit")
    @ApiOperation(value = "사용자 회원탈퇴")
    public ResponseEntity<String> quitUser(HttpServletRequest request){
        userService.quitUser(request);
        return ResponseEntity.status(200).body("사용자 회원탈퇴 성공");
    }

    @PatchMapping("/password")
    @ApiOperation(value = "사용자 비밀번호 찾기")
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
