package com.qupp.post.dto.request;

import com.qupp.post.repository.Answer;
import com.qupp.post.repository.Question;
import com.qupp.user.repository.User;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Schema(description = "답변글 등록 요청 폼")
public class RequestRegisterAnswer {

    @Schema(description = "내용", type = "String", example = "content register")
    private String content;

    @Schema(description = "작성자", type = "String", example = "user")
    private String author;

    @ApiModelProperty(hidden = true)
    private Question question;

    @ApiModelProperty(hidden = true)
    private User user;

    public Answer toEntity(LocalDateTime registerTime, LocalDateTime updateTime) {
        return Answer.builder()
                .content(content)
                .user(user)
                .registerTime(registerTime)
                .updateTime(updateTime)
                .question(question)
                .build();
    }
}
