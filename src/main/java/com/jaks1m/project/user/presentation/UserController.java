package com.jaks1m.project.user.presentation;

import com.jaks1m.project.aws.application.dto.ImageDto;
import com.jaks1m.project.user.presentation.dto.UserEditRequest;
import com.jaks1m.project.user.presentation.dto.UserEditPasswordRequest;
import com.jaks1m.project.user.presentation.dto.UserFindPasswordRequest;
import com.jaks1m.project.user.presentation.dto.UserCreateRequest;
import com.jaks1m.project.user.application.dto.UserCreateResponse;
import com.jaks1m.project.user.application.dto.UserResponse;
import com.jaks1m.project.common.domain.BaseResponse;
import com.jaks1m.project.aws.application.AwsS3Service;
import com.jaks1m.project.user.application.UserService;
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


    @PostMapping
    @ApiOperation(value = "사용자 회원가입")
    public ResponseEntity<BaseResponse<UserCreateResponse>> join(@RequestBody @Validated UserCreateRequest request) {
        UserCreateResponse userCreateResponse = userService.joinUser(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.<UserCreateResponse>builder()
                        .status(200)
                        .body(userCreateResponse)
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
    public ResponseEntity<BaseResponse<UserResponse>> findUser(){
        UserResponse userResponse =userService.findUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.<UserResponse>builder()
                        .status(200)
                        .body(userResponse)
                        .build());
    }

    @PatchMapping("/me")
    @ApiOperation(value = "사용자 정보수정")
    public ResponseEntity<String> editUser(@RequestBody @Validated UserEditRequest request){
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
    public ResponseEntity<String> findPassword(UserFindPasswordRequest request){
        userService.findPassword(request);
        return ResponseEntity.status(200).body("사용자 비밀번호 찾기 성공");
    }

    @PatchMapping("/me/password")
    @ApiOperation(value = "사용자 비밀번호 변경")
    @ResponseBody
    public ResponseEntity<String> editPassword(@RequestBody @Validated UserEditPasswordRequest request){
        userService.editPassword(request);
        return ResponseEntity.status(200).body("사용자 비밀번호 변경 성공");
    }
}
