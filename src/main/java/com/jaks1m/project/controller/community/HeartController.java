package com.jaks1m.project.controller.community;

import com.jaks1m.project.service.community.HeartService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/heart")
public class HeartController {
    private final HeartService heartService;

    @PostMapping("/{boardId}")
    @ApiOperation("게시글 좋아요 등록")
    public ResponseEntity<String> addHeart(@PathVariable Long boardId){
        heartService.addHeart(boardId);
        return ResponseEntity.status(200).body("게시글 좋아요 등록 성공");
    }

    @PostMapping("/{boardId}")
    @ApiOperation("게시글 좋아요 삭제")
    public ResponseEntity<String> deleteHeart(@PathVariable Long boardId){
        heartService.deleteHeart(boardId);
        return ResponseEntity.status(200).body("게시글 좋아요 삭제 성공");
    }
}
