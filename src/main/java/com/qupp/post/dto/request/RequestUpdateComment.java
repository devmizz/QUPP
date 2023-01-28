package com.qupp.post.dto.request;

import com.qupp.post.repository.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Schema(description = "댓글 수정 요청 폼")
public class RequestUpdateComment {

    @Schema(description = "댓글", type = "String", example = "comment update")
    private String comment;

}
