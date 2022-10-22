package com.jaks1m.project.schedule.domain;

import com.jaks1m.project.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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
    @DateTimeFormat(pattern = "HH-mm")
    private LocalTime start;
    @NotNull
    private LocalTime end;
    @NotEmpty
    private String content;
    @NotNull
    private LocalDate createDate;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    public void updateTime(LocalTime start, LocalTime end){
        this.start=start;
        this.end=end;
    }

    public void updateContent(String content){
        this.content=content;
    }

    public void deleteUser(){
        this.user=null;
    }

    @Builder
    public Schedule(LocalTime start, LocalTime end, String content, User user,LocalDate createDate) {
        this.start = start;
        this.end = end;
        this.content = content;
        this.user = user;
        this.createDate=createDate;
    }
}
