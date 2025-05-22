package com.example.demo.member.ctrl.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AdminKey {
    @NotNull(message = "adminKey는 필수입니다.")
    String adminKey;
}
