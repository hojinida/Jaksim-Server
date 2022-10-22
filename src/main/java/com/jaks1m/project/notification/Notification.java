package com.jaks1m.project.notification;

import com.jaks1m.project.common.domain.BaseEntity;
import com.jaks1m.project.notification.domain.NotificationType;
import com.jaks1m.project.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "NOTICE_SEQ_GENERATOR", sequenceName = "NOTICE_SEQ")
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "NOTICE_SEQ_GENERATOR")
    private Long id;
    private String message;
    private Long valueId;
    private boolean checked;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public void updateChecked(){
        this.checked=true;
    }
    @Builder
    public Notification(String message, boolean checked, User user,Long valueId ,NotificationType notificationType) {
        this.message = message;
        this.checked = checked;
        this.user = user;
        this.valueId=valueId;
        this.notificationType = notificationType;
    }
}
