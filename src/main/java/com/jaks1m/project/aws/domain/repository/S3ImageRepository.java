package com.jaks1m.project.aws.domain.repository;

import com.jaks1m.project.aws.domain.S3Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface S3ImageRepository extends JpaRepository<S3Image,Long> {

}
