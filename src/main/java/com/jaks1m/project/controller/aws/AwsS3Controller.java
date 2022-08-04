package com.jaks1m.project.controller.aws;

import com.jaks1m.project.domain.entity.aws.Category;
import com.jaks1m.project.service.aws.AwsS3Service;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class AwsS3Controller {
    private final AwsS3Service awsS3Service;
    @GetMapping("/image")
    @ApiOperation(value = "이미지 불러오기")
    public String loadImage(@RequestParam Category category){
        return awsS3Service.getImages(category);
    }

    @PostMapping("/image")
    @ApiOperation(value = "이미지 저장")
    public String saveImage(@RequestPart("file") MultipartFile multipartFile, @RequestParam Category category) throws IOException {
        return awsS3Service.upload(multipartFile,"upload",category);
    }

    @DeleteMapping("/image")
    @ApiOperation(value = "이미지 삭제")
    public Boolean deleteImage(@RequestParam Category category){
        awsS3Service.remove(category);
        return true;
    }
}
