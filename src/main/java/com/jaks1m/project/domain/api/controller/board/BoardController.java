package com.jaks1m.project.domain.api.controller.board;

import com.jaks1m.project.domain.api.dto.board.BoardResponse;
import com.jaks1m.project.domain.common.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BoardController {
//    private final
//    @GetMapping("/boards")
//    @ApiOperation(value = "게시판")
//    public ResponseEntity<BaseResponse<BoardResponse>> show(){
//
//        return ResponseEntity.status(200)
//                .build();
//    }
}
