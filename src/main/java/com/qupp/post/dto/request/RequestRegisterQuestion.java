package com.qupp.post.dto.request;

import com.qupp.post.repository.College;
import com.qupp.post.repository.Dept;
import com.qupp.post.repository.Question;
import com.qupp.user.repository.User;
import com.qupp.validator.Enum;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "질문글 등록 요청 폼")
public class RequestRegisterQuestion {
    @Schema(description = "작성자", type = "String", example = "user")
    @NotNull(message = "제목이 입력되지 않았습니다.")
    private String title;

    @Schema(description = "내용", type = "String", example = "content register")
    @NotNull(message = "내용이 입력되지 않았습니다.")
    private String content;

    @Schema(description = "작성자", type = "String", example = "user")
    @NotNull(message = "작성자가 입력되지 않았습니다.")
    private String author;

    @Schema(description = "대분류", type = "String", allowableValues = {"Humanities", "SocialScience", "Business", "NaturalScience", "Engineering", "Art"})
    @Enum(enumClass = College.class, ignoreCase = true)
    private String college;

    @Schema(description = "소분류", type = "String", allowableValues = {"Humanities", "SocialScience", "Business", "NaturalScience", "Engineering", "Art"})
    @Enum(enumClass = Dept.class, ignoreCase = true)
    private String dept;

    @ApiModelProperty(hidden = true)
    private User user;

    public Question toEntity(LocalDateTime registerTime, LocalDateTime updateTime) {
        return Question.builder()
                .title(title)
                .content(content)
                .user(user)
                .registerTime(registerTime)
                .updateTime(updateTime)
                .build();
    }
}
