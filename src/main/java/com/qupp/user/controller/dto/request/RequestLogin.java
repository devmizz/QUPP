package com.qupp.user.controller.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "로그인 요청")
public class RequestLogin {

    @Schema(description = "사용자 email", type = "string", example = "qupp@gmail.com")
    private String email;
    @Schema(description = "사용자 password", type = "string", example = "quppteam")
    private String password;

}
