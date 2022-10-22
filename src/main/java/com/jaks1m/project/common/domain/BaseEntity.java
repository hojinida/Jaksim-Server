package com.jaks1m.project.common.domain;

import com.jaks1m.project.user.domain.Status;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Column(length = 10, columnDefinition = "varchar(10) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdData;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public void updateStatus(Status status) {
        this.status=status;
    }
}
