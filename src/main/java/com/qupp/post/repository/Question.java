package com.qupp.post.repository;

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
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Where(clause = "deleted = false")
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String title;

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
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Question(long id, String title, String content, User user, LocalDateTime registerTime, LocalDateTime updateTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.registerTime = registerTime;
        this.updateTime = updateTime;
    }

    public void delete() {
        this.deleted = true;
    }

    public void addCategory(Category category) {
        this.category = category;
    }
}
