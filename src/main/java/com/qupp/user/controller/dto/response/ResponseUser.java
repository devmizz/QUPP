package com.qupp.user.controller.dto.response;

import com.qupp.user.repository.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Schema(description = "유저 응답")
public class ResponseUser {
    @Schema(description = "사용자 id")
    private long id;

    @Schema(description = "사용자 이메일")
    private String email;

    @Schema(description = "사용자 닉네임")
    private String nickname;

    public static ResponseUser fromEntity(User user) {
        return ResponseUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .build();
    }

    @Builder
    public ResponseUser(long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

}