package com.qupp.user.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "프로필 수정 응답")
public class ResponseUserUpdate {

    @Schema(description = "수정된 이메일", type = "string", example = "quppupdated@gmail.com")
    private String email;

    @Schema(description = "수정된 닉네임", type = "string", example = "quppupdated")
    private String nickname;
}
