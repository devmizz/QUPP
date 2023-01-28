package com.qupp.post.dto.request;

import com.qupp.post.repository.Answer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Schema(description = "답변글 수정 요청 폼")
public class RequestUpdateAnswer {

    @Schema(description = "내용", type = "String", example = "content update")
    private String content;

    public Answer toEntity(LocalDateTime registerTime, LocalDateTime updateTime) {
        return Answer.builder()
                .content(content)
                .registerTime(registerTime)
                .updateTime(updateTime)
                .build();
    }
}
