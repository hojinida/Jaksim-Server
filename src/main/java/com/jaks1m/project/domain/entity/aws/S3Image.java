package com.jaks1m.project.domain.entity.aws;

import com.jaks1m.project.domain.entity.community.Board;
import com.jaks1m.project.domain.entity.user.BaseEntity;
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
    private Long id;
    @Nullable
    private String imageKey;
    @Nullable
    private String imagePath;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;
    public void updateKey(String imageKey){
        this.imageKey=imageKey;
    }
    public void updatePath(String imagePath){
        this.imagePath=imagePath;
    }
    @Builder
    public S3Image(@Nullable String imageKey, @Nullable String imagePath, @Nullable Board board) {
        this.imageKey = imageKey;
        this.imagePath = imagePath;
        this.board=board;
    }
}
