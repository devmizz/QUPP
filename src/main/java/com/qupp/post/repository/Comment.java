package com.qupp.post.repository;

import com.qupp.post.dto.response.ResponseAnswer;
import com.qupp.post.dto.response.ResponseQuestion;
import com.qupp.user.repository.User;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Where(clause = "deleted = false")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String comment;

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
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @Builder
    public Comment(long id, String comment, User user, LocalDateTime registerTime, LocalDateTime updateTime, Question question, Answer answer) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.registerTime = registerTime;
        this.updateTime = updateTime;
        this.question = question;
        this.answer = answer;
    }

    public void delete() {
        this.deleted = true;
    }

    public void addQuestion(ResponseQuestion responseQuestion) {
        this.question = responseQuestion.toEntity();
    }

    public void addAnswer(ResponseAnswer responseAnswer) {
        this.answer = responseAnswer.toEntity();
    }

}
