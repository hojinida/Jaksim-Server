package com.jaks1m.project.follow.presentation;

import com.jaks1m.project.follow.application.dto.FollowResponse;
import com.jaks1m.project.follow.application.FollowService;
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
@RequestMapping("/api/v1")
public class FollowController {
    private final FollowService followService;
    @PostMapping("/follow")
    @ApiOperation("follow 신청")
    public ResponseEntity<String> addFollow(@RequestParam @Validated Long id){
        followService.addFollow(id);
        return ResponseEntity.status(200).body("follow 성공");
    }

    @GetMapping("/follower")
    @ApiOperation("follower 조회")
    public ResponseEntity<List<FollowResponse>> getFollowers(){
        return ResponseEntity.status(200).body(followService.getFollowers());
    }

    @GetMapping("/follow")
    @ApiOperation("follow 조회")
    public ResponseEntity<List<FollowResponse>> getFollows(){
        return ResponseEntity.status(200).body(followService.getFollows());
    }

    @DeleteMapping("/follow")
    @ApiOperation("unfollow")
    public ResponseEntity<String> unFollow(@RequestParam @Validated Long id){
        followService.unFollow(id);
        return ResponseEntity.status(200).body("unfollow 성공");
    }

    @DeleteMapping("/follower")
    @ApiOperation("follower 삭제")
    public ResponseEntity<String> deleteFollower(@RequestParam @Validated Long id){
        followService.deleteFollower(id);
        return ResponseEntity.status(200).body("follower 삭제 성공");
    }
}
