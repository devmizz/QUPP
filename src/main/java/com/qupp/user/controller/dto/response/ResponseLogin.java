package com.qupp.user.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "로그인 응답")
public class ResponseLogin {
    @Schema(description = "사용자")
    private ResponseUser responseUser;
    @Schema(description = "jwt 토큰")
    private String jwtToken;


    @Builder
    public ResponseLogin(ResponseUser responseUser, String jwtToken) {
        this.responseUser = responseUser;
        this.jwtToken = jwtToken;
    }
}
