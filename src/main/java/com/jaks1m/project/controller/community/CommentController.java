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
@RequestMapping("/api/v1/boards/{id}/comment")
public class CommentController {
    private final CommentService commentService;
    @PostMapping
    @ApiOperation(value = "댓글 등록")
    public ResponseEntity<String> addComment(@RequestBody @Validated CommentAddRequestDto request, @PathVariable Long id){
        commentService.addComment(request,id);
        return ResponseEntity.status(200).body("댓글 등록 성공");
    }
}
