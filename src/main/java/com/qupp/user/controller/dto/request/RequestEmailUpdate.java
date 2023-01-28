package com.qupp.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(description = "이메일 수정 요청")
public class RequestEmailUpdate {
    @Schema(description = "수정 요청 이메일", type = "string", example = "quppupdated@gmail.com")
    private String email;
}
