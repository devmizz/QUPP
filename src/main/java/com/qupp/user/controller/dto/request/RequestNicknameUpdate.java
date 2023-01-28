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
@Schema(description = "닉네임 수정 요청")
public class RequestNicknameUpdate {
    @Schema(description = "수정 요청 닉네임", type = "string", example = "quppupdated")
    private String nickname;
    }
