package com.jaks1m.project.domain.api.entity.aws;

import com.jaks1m.project.domain.api.entity.user.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@SequenceGenerator(name = "S3_IMAGE_SEQ_GENERATOR", sequenceName = "S3_IMAGE_SEQ")
public class S3Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "S3_IMAGE_SEQ_GENERATOR")
    @Column(name = "S3_IMAGE_ID")
    private Long id;
    @Nullable
    private String imageKey;
    @Nullable
    private String imagePath;
    public void updateKey(String key){
        this.imageKey=key;
    }
    public void updatePath(String path){
        this.imagePath=path;
    }
    @Builder
    public S3Image(@Nullable String imageKey, @Nullable String imagePath) {
        this.imageKey = imageKey;
        this.imagePath = imagePath;
    }
}
