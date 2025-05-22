package com.example.demo.member.ctrl.req;

import jakarta.validation.constraints.NotNull;


public record RegisterReq(
        @NotNull(message = "인가 accessToken은 필수입니다.")
        String accessToken
) {
}
