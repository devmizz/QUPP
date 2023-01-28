package com.qupp.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qupp.post.repository.Answer;
import com.qupp.user.controller.dto.response.ResponseUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "답변글 응답 폼")
public class ResponseAnswer {
    @Schema(description = "번호")
    private long id;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "작성자 정보")
    private ResponseUser user;

    @Schema(description = "등록시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime registerTime;

    @Schema(description = "수정시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updateTime;

    @Schema(description = "댓글")
    private List<ResponseComment> comments;

    public ResponseAnswer fromEntity(Answer a) {
        return ResponseAnswer.builder()
                .id(a.getId())
                .content(a.getContent())
                .registerTime(a.getRegisterTime())
                .updateTime(a.getUpdateTime())
                .build();
    }

    public Answer toEntity() {
        return Answer.builder()
                .id(id)
                .content(content)
                .user(user.toEntity())
                .registerTime(registerTime)
                .updateTime(updateTime)
                .build();
    }

    @Builder
    public ResponseAnswer(long id, String content, LocalDateTime registerTime, LocalDateTime updateTime) {
        this.id = id;
        this.content = content;
        this.registerTime = registerTime;
        this.updateTime = updateTime;
    }
}