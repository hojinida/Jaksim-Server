package com.jaks1m.project.domain.api.service;

import com.amazonaws.services.s3.AmazonS3Client;
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
    private final AmazonS3Client amazonS3Client;
    private final UserRepository userRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String getImages(Category category){
        if(category==Category.USER){
            User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                    .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
            return user.getImage();
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
        String url;
        if(category==Category.USER){
            User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                    .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
            url=user.getImage();
        }else{
            url="1";
        }
        if (!amazonS3Client.doesObjectExist(bucket, url)) {
            throw new CustomException(ErrorCode.IMAGE_NOT_FOUND);
        }
        amazonS3Client.deleteObject(bucket, url);
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
        System.out.println("###############################################");
        System.out.println(key);
        String path = putS3(file, key);
        System.out.println(path);
        if(category==Category.USER){
            User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                    .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
            if(user.getImage()!=null){
                remove(Category.USER);
            }
            user.updateImage(path);
        }
        removeFile(file);

        return path;
    }
    private String randomFileName(File file, String dirName) {
        return dirName + "/" + UUID.randomUUID() + file.getName();
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
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
