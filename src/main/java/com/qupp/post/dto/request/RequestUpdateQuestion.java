package com.qupp.post.dto.request;

import com.qupp.post.repository.Question;
import com.qupp.user.repository.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Schema(description = "질문글 수정 요청 폼")
public class RequestUpdateQuestion {
    @Schema(description = "제목", type = "String", example = "title update")
    private String title;

    @Schema(description = "내용", type = "String", example = "content update")
    private String content;

    public Question toEntity(LocalDateTime registerTime, LocalDateTime updateTime) {
        return Question.builder()
                .title(title)
                .content(content)
                .registerTime(registerTime)
                .updateTime(updateTime)
                .build();
    }
}
