package com.jaks1m.project.domain.api.entity.aws;

import com.jaks1m.project.domain.api.entity.user.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@SequenceGenerator(name = "IMAGE_SEQ_GENERATOR", sequenceName = "IMAGE_SEQ")
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "IMAGE_SEQ_GENERATOR")
    @Column(name = "IMAGE_ID")
    private Long id;
    private String key;
    private String path;

    public void updateKey(String key){
        this.key=key;
    }

    public void updatePath(String path){
        this.path=path;
    }

    @Builder
    public Image(String key, String path) {
        this.key = key;
        this.path = path;
    }
}
