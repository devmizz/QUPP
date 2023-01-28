package com.qupp.user.controller.dto.request;

import com.qupp.user.repository.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원가입 요청")
public class RequestCreateUser {

    @Schema(description = "사용자 email", type = "string", example = "qupp@gmail.com")
    private String email;

    @Schema(description = "사용자 email", type = "string", example = "qupp")
    private String nickname;

    @Schema(description = "사용자 password", type = "string", example = "quppteam")
    private String password;


    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .build();
    }
}