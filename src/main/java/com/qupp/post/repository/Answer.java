package com.qupp.post.repository;

import com.qupp.post.dto.response.ResponseQuestion;
import com.qupp.user.repository.User;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Where(clause = "deleted = false")
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String content;

    @Column
    @NotNull
    private LocalDateTime registerTime;

    @Column
    @NotNull
    private LocalDateTime updateTime;

    @Column
    @NotNull
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="question_id")
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Builder
    public Answer(long id, String content, User user, LocalDateTime registerTime, LocalDateTime updateTime, Question question) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.registerTime = registerTime;
        this.updateTime = updateTime;
        this.question = question;
    }

    public void delete() {
        this.deleted = true;
    }

    public void addQuestion(ResponseQuestion responseQuestion) {
        this.question = responseQuestion.toEntity();
    }
}
