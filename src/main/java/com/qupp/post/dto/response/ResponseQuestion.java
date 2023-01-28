package com.qupp.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qupp.post.repository.Comment;
import com.qupp.post.repository.Question;
import com.qupp.user.controller.dto.response.ResponseUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "질문에 대한 정보")
public class ResponseQuestion {
    @Schema(description = "번호")
    private long id;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "작성자 정보")
    private ResponseUser user;

    @Schema(description = "대분류")
    private String category;

    @Schema(description = "소분류")
    private String subCategory;

    @Schema(description = "등록시각")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime registerTime;

    @Schema(description = "수정시각")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updateTime;

    @Schema(description = "댓글")
    private List<ResponseComment> comments;

    public ResponseQuestion fromEntity(Question q) {
        return ResponseQuestion.builder()
                .id(q.getId())
                .title(q.getTitle())
                .content(q.getContent())
                .registerTime(q.getRegisterTime())
                .updateTime(q.getUpdateTime())
                .build();
    }

    public Question toEntity() {
        return Question.builder()
                .id(id)
                .title(title)
                .content(content)
                .user(user.toEntity())
                .registerTime(registerTime)
                .updateTime(updateTime)
                .build();
    }

    @Builder
    public ResponseQuestion(long id, String title, String content, LocalDateTime registerTime, LocalDateTime updateTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.registerTime = registerTime;
        this.updateTime = updateTime;
    }
}
