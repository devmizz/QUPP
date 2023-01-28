package com.qupp.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "질문글 상세 조회")
public class ResponsePost {

    @Schema(description = "질문 정보")
    ResponseQuestion question;

    @Schema(description = "답변 정보")
    List<ResponseAnswer> answers;

    public ResponsePost(ResponseQuestion question, List<ResponseAnswer> answers) {
        this.question = question;
        this.answers = answers;
    }
}
