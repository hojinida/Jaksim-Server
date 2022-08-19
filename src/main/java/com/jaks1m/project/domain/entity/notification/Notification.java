package com.jaks1m.project.domain.entity.notification;

import com.jaks1m.project.domain.entity.user.BaseEntity;
import com.jaks1m.project.domain.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "NOTICE_SEQ_GENERATOR", sequenceName = "NOTICE_SEQ")
public class Notification<T> extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "NOTICE_SEQ_GENERATOR")
    private Long id;
    private String message;
    private T value;
    private boolean checked;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
    @Builder
    public Notification(String message, boolean checked, User user,T value ,NotificationType notificationType) {
        this.message = message;
        this.checked = checked;
        this.user = user;
        this.value=value;
        this.notificationType = notificationType;
    }
}
