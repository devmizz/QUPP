package com.qupp.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qupp.post.repository.Comment;
import com.qupp.user.controller.dto.response.ResponseUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Schema(description = "댓글 응답 폼")
public class ResponseComment {
    @Schema(description = "번호")
    private long id;

    @Schema(description = "댓글")
    private String comment;

    @Schema(description = "작성자 정보")
    private ResponseUser user;

    @Schema(description = "등록시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime registerTime;

    @Schema(description = "수정시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updateTime;

    public ResponseComment fromEntity(Comment c) {
        return ResponseComment.builder()
                .id(c.getId())
                .comment(c.getComment())
                .registerTime(c.getRegisterTime())
                .updateTime(c.getUpdateTime())
                .build();
    }

    @Builder
    public ResponseComment(long id, String comment, LocalDateTime registerTime, LocalDateTime updateTime) {
        this.id = id;
        this.comment = comment;
        this.registerTime = registerTime;
        this.updateTime = updateTime;
    }
}
