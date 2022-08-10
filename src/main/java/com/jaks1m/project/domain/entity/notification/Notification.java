package com.jaks1m.project.domain.entity.notification;

import com.jaks1m.project.domain.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "NOTICE_SEQ_GENERATOR", sequenceName = "NOTICE_SEQ")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "NOTICE_SEQ_GENERATOR")
    private Long id;

    private String title;

    private String message;

    private boolean checked;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
}
