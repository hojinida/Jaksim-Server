package com.jaks1m.project.board.presentation;

import com.jaks1m.project.board.presentation.dto.BoardCreateRequest;
import com.jaks1m.project.board.application.dto.BoardResponse;
import com.jaks1m.project.board.domain.BoardType;
import com.jaks1m.project.aws.application.dto.ImageDto;
import com.jaks1m.project.aws.application.AwsS3Service;
import com.jaks1m.project.board.application.BoardService;
import com.jaks1m.project.common.domain.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController {
    private final BoardService boardService;
    private final AwsS3Service awsS3Service;
    @GetMapping
    @ApiOperation(value = "게시판 목록")
    public ResponseEntity<BaseResponse<List<BoardResponse>>> getListBoard(@PageableDefault(size = 5,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.status(200)
                .body(BaseResponse.<List<BoardResponse>>builder()
                        .status(200)
                        .body(boardService.getBoardList(pageable))
                        .build());
    }

    @GetMapping("/list")
    @ApiOperation(value = "게시판 상세")
    public ResponseEntity<BaseResponse<List<BoardResponse>>> getBoards(@PageableDefault(size = 5,sort = "id",direction = Sort.Direction.DESC) Pageable pageable
            ,@RequestParam @Validated BoardType boardType){
        return ResponseEntity.status(200)
                .body(BaseResponse.<List<BoardResponse>>builder()
                        .status(200)
                        .body(boardService.getBoards(boardType,pageable))
                        .build());
    }

    @PostMapping("/list")
    @ApiOperation(value = "게시글 등록")
    public ResponseEntity<String> addBoard(@RequestPart(value = "files",required = false) List<MultipartFile> multipartFiles,
            @RequestPart(value = "boardAddRequestDto") @Validated BoardCreateRequest request) throws IOException {
        List<ImageDto> images=awsS3Service.upload(multipartFiles,"upload");
        boardService.addBoard(images,request);
        return ResponseEntity.status(200).body("게시글 등록 성공");
    }

    @GetMapping("/list/{id}")
    @ApiOperation(value = "게시글 상세")
    public ResponseEntity<BaseResponse<BoardResponse>> getBoard(@PathVariable Long id){
        return ResponseEntity.status(200)
                .body(BaseResponse.<BoardResponse>builder()
                        .body(boardService.getBoard(id))
                        .build());
    }

    @PutMapping("/list/{id}")
    @ApiOperation(value = "게시글 수정")
    public ResponseEntity<BaseResponse<BoardResponse>> editBoard(@RequestPart(value = "files",required = false) List<MultipartFile> multipartFiles,
                                                                 @RequestPart(value = "boardAddRequestDto") @Validated BoardCreateRequest request, @PathVariable Long id) throws IOException {
        List<ImageDto> images=awsS3Service.upload(multipartFiles,"upload");
        return ResponseEntity.status(200)
                .body(BaseResponse.<BoardResponse>builder()
                        .body(boardService.editBoard(images,request,id))
                        .build());
    }

    @PatchMapping("/list/{id}")
    @ApiOperation(value = "게시글 삭제")
    public Boolean deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
        return true;
    }
}
