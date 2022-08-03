package com.jaks1m.project.domain.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jaks1m.project.domain.api.entity.aws.Category;
import com.jaks1m.project.domain.api.entity.user.User;
import com.jaks1m.project.domain.api.repository.UserRepository;
import com.jaks1m.project.domain.config.security.SecurityUtil;
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
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AwsS3Service {
    private final AmazonS3 amazonS3;
    private final UserRepository userRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String getImages(Category category){
        if(category==Category.USER){
            User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                    .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
            return user.getImage().getPath();
        }else{
            return "d";
        }
    }

    @Transactional
    public String upload(MultipartFile multipartFile, String dirName, Category category) throws IOException {
        File file = convertMultipartFileToFile(multipartFile)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_CONVERT_FILE));

        return upload(file, dirName,category);
    }
    @Transactional
    public void remove(Category category) {
        String key;
        if(category==Category.USER){
            User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                    .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
            key=user.getImage().getKey();
        }else{
            key="1";
        }
        if (!amazonS3.doesObjectExist(bucket,key)) {
            throw new CustomException(ErrorCode.IMAGE_NOT_FOUND);
        }
        amazonS3.deleteObject(bucket,key);
    }

    private void removeFile(File file) {
        if(file.delete()){
            log.info("파일이 삭제 되었습니다.");
        }else{
            log.info("파일이 삭제되지 않았습니다.");
        }
    }

    private String upload(File file, String dirName, Category category) {
        String key = randomFileName(file, dirName);
        String path = putS3(file, key);
        if(category==Category.USER){
            User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                    .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
            if(user.getImage()!=null){
                remove(Category.USER);
            }
            user.updateImage(key,path);
        }
        removeFile(file);

        return path;
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
