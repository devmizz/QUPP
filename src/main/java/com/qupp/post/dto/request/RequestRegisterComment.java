package com.qupp.post.dto.request;

import com.qupp.post.repository.Answer;
import com.qupp.post.repository.Comment;
import com.qupp.post.repository.Question;
import com.qupp.user.repository.User;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Schema(description = "댓글 등록 요청 폼")
public class RequestRegisterComment {

    @Schema(description = "댓글", type = "String", example = "comment register")
    private String comment;

    @Schema(description = "작성자", type = "String", example = "user")
    private String author;

    @ApiModelProperty(hidden = true)
    private User user;

    @ApiModelProperty(hidden = true)
    private Question question;

    @ApiModelProperty(hidden = true)
    private Answer answer;

    public Comment toEntity(LocalDateTime registerTime, LocalDateTime updateTime) {
        return Comment.builder()
                .comment(comment)
                .user(user)
                .registerTime(registerTime)
                .updateTime(updateTime)
                .question(question)
                .answer(answer)
                .build();
    }

}
