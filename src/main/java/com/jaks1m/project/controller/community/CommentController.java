package com.jaks1m.project.controller.community;

import com.jaks1m.project.dto.community.request.CommentAddRequestDto;
import com.jaks1m.project.service.community.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards/comment")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("{boardId}")
    @ApiOperation(value = "댓글 등록")
    public ResponseEntity<String> addComment(@RequestBody @Validated CommentAddRequestDto request, @PathVariable Long boardId){
        commentService.addComment(request,boardId);
        return ResponseEntity.status(200).body("댓글 등록 성공");
    }

    @PatchMapping("/{commentId}")
    @ApiOperation(value = "댓글 수정")
    public ResponseEntity<String> editComment(@RequestBody @Validated CommentAddRequestDto request,@PathVariable Long commentId){
        commentService.editComment(request,commentId);
        return ResponseEntity.status(200).body("댓글 수정 성공");
    }

    @DeleteMapping("/{commentId}")
    @ApiOperation(value = "댓글 삭제")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.status(200).body("댓글 삭제 성공");
    }
}
