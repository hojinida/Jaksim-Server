package com.jaks1m.project.repository.func;

import com.jaks1m.project.domain.entity.aws.S3Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface S3ImageRepository extends JpaRepository<S3Image,Long> {

}
