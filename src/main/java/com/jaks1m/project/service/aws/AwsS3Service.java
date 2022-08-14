package com.jaks1m.project.service.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jaks1m.project.domain.entity.aws.Category;
import com.jaks1m.project.domain.entity.aws.S3Image;
import com.jaks1m.project.domain.entity.community.Board;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.dto.community.response.ImageDto;
import com.jaks1m.project.repository.user.UserRepository;
import com.jaks1m.project.config.security.SecurityUtil;
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
    public List<ImageDto> upload(List<MultipartFile> multipartFiles, String dirName, Category category) throws IOException {
        List<ImageDto> images=new ArrayList<>();
        for(MultipartFile multipartFile:multipartFiles){
            File file = convertMultipartFileToFile(multipartFile)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_CONVERT_FILE));
            images.add(upload(file, dirName,category));
        }
        return images;
    }
    @Transactional
    public void remove() {
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        String key=user.getS3Image().getImageKey();
        user.updateImage(null,null);
        if(!Objects.equals(user.getHomeGround(), "web")){
            return;
        }
        if (!amazonS3.doesObjectExist(bucket,key)) {
            throw new CustomException(ErrorCode.IMAGE_NOT_FOUND);
        }
        amazonS3.deleteObject(bucket,key);
    }
    @Transactional
    public void remove(Board board) {
        List<String> keys = board.getKeys();
        for(String key:keys){
            if (!amazonS3.doesObjectExist(bucket,key)) {
                throw new CustomException(ErrorCode.IMAGE_NOT_FOUND);
            }
            amazonS3.deleteObject(bucket,key);
        }
    }

    private void removeFile(File file) {
        if(file.delete()){
            log.info("파일이 삭제 되었습니다.");
        }else{
            log.info("파일이 삭제되지 않았습니다.");
        }
    }

    private ImageDto upload(File file, String dirName, Category category) {
        String key = randomFileName(file, dirName);
        String path = putS3(file, key);
        if(category==Category.USER) {
            User user = userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
            if (user.getS3Image().getImagePath() != null) {
                remove();
            }
            user.updateImage(key, path);
        }
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
