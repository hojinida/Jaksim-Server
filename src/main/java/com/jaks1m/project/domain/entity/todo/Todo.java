package com.jaks1m.project.domain.entity.todo;

import com.jaks1m.project.domain.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
@SequenceGenerator(name = "TODO_SEQ_GENERATOR", sequenceName = "TODO_SEQ")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "USER_SEQ_GENERATOR")
    private Long id;

    private LocalDate createDate;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Boolean completed;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    public void updateTitle(String title){
        this.title=title;
    }

    public void updateCompleted(Boolean completed){
        this.completed=completed;
    }

    @Builder
    public Todo(LocalDate createDate, String title, Boolean completed, User user) {
        this.createDate = createDate;
        this.title = title;
        this.completed = completed;
        this.user = user;
    }
}
