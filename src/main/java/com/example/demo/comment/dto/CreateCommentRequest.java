package com.example.demo.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCommentRequest(
        @NotBlank(message = "댓글 내용은 필수입니다.") String content,

        @NotNull(message = "회원 ID는 필수입니다.") Long memberRowId,

        Long parentRowId) {
}
