package com.qupp.user.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "API 응답 폼")
public class Response<T> {
    @Schema(description = "응답 메세지")
    private String msg;

    @Builder
    public static <T> Response<T> Response(String msg) {
        return new Response<>(msg);
    }
}
