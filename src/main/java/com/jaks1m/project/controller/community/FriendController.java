package com.jaks1m.project.controller.community;

import com.jaks1m.project.dto.community.response.FriendResponseDto;
import com.jaks1m.project.service.community.FriendService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friend")
public class FriendController {
    private final FriendService friendService;

    @PostMapping
    @ApiOperation("친구 등록")
    public ResponseEntity<String> addFriend(@RequestParam @Validated Long id){
        friendService.addFriend(id);
        return ResponseEntity.status(200).body("친구 등록 성공");
    }

    @GetMapping
    @ApiOperation("친구 조회")
    public ResponseEntity<List<FriendResponseDto>> addFriend(){
        return ResponseEntity.status(200).body(friendService.getFriends());
    }

    @DeleteMapping
    @ApiOperation("친구 삭제")
    public ResponseEntity<String> deleteFriend(@RequestParam @Validated Long id){

        return ResponseEntity.status(200).body("친구 삭제 성공");
    }
}
