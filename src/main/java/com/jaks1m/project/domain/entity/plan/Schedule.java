package com.jaks1m.project.domain.entity.plan;

import com.jaks1m.project.domain.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@Entity
@NoArgsConstructor
@SequenceGenerator(name = "SCHEDULE_SEQ_GENERATOR", sequenceName = "SCHEDULE_SEQ")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SCHEDULE_SEQ_GENERATOR")
    private Long id;
    @NotNull
    private LocalTime start;
    @NotNull
    private LocalTime end;
    @NotEmpty
    private String content;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public Schedule(LocalTime start, LocalTime end, String content, User user) {
        this.start = start;
        this.end = end;
        this.content = content;
        this.user = user;
    }
}
