package com.jaks1m.project.domain.api.controller.board;

import com.jaks1m.project.domain.api.dto.board.BoardPostRequestDto;
import com.jaks1m.project.domain.api.dto.board.BoardResponse;
import com.jaks1m.project.domain.api.entity.board.BoardType;
import com.jaks1m.project.domain.api.service.BoardService;
import com.jaks1m.project.domain.common.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BoardController {
    private final BoardService boardService;
    @GetMapping("/boards")
    @ApiOperation(value = "게시판 목록")
    public ResponseEntity<BaseResponse<List<BoardResponse>>> listBoard(@PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.status(200)
                .body(BaseResponse.<List<BoardResponse>>builder()
                        .status(200)
                        .body(boardService.getList(pageable))
                        .build());
    }

    @PostMapping("/boards")
    @ApiOperation(value = "게시판")
    public ResponseEntity<BaseResponse<List<BoardResponse>>> getBoard(@PageableDefault(size = 5) Pageable pageable
            ,@RequestParam @Validated BoardType boardType){
        return ResponseEntity.status(200)
                .body(BaseResponse.<List<BoardResponse>>builder()
                        .status(200)
                        .body(boardService.getBoard(boardType,pageable))
                        .build());
    }

    @PostMapping("/boards/post")
    @ApiOperation(value = "게시글 등록")
    public Boolean postBoard(@RequestBody @Validated BoardPostRequestDto request){
        boardService.postBoard(request);
        return true;
    }
}
