package com.jaks1m.project.service.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jaks1m.project.dto.community.response.ImageDto;
import com.jaks1m.project.repository.user.UserRepository;
import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AwsS3Service {
    private final AmazonS3 amazonS3;
    private final UserRepository userRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Transactional
    public List<ImageDto> upload(List<MultipartFile> multipartFiles, String dirName) throws IOException {
        List<ImageDto> images=new ArrayList<>();
        for(MultipartFile multipartFile:multipartFiles){
            File file = convertMultipartFileToFile(multipartFile)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_CONVERT_FILE));
            images.add(upload(file, dirName));
        }
        return images;
    }

    private void removeFile(File file) {
        if(file.delete()){
            log.info("파일이 삭제 되었습니다.");
        }else{
            log.info("파일이 삭제되지 않았습니다.");
        }
    }

    private ImageDto upload(File file, String dirName) {
        String key = randomFileName(file, dirName);
        String path = putS3(file, key);
        removeFile(file);
        return ImageDto.builder().key(key).path(path).build();
    }

    private String randomFileName(File file, String dirName) {
        return dirName + "/" + UUID.randomUUID() + file.getName();
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)){
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }
}
