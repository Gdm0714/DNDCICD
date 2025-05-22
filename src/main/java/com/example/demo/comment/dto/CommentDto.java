package com.example.demo.comment.dto;

import lombok.Builder;
import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.comment.entity.Comment;

public class CommentDto {

    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        @NotBlank(message = "댓글 내용은 필수입니다.")
        private String content;

        @NotBlank(message = "작성자는 필수입니다.")
        @Size(max = 50, message = "작성자명은 50자 이하여야 합니다.")
        private String author;

        private Long parentId;

        @Builder
        public CreateRequest(String content, String author, Long parentId) {
            this.content = content;
            this.author = author;
            this.parentId = parentId;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {
        @NotBlank(message = "댓글 내용은 필수입니다.")
        private String content;

        @Builder
        public UpdateRequest(String content) {
            this.content = content;
        }
    }

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String content;
        private String author;
        private Long postId;
        private Long parentId;
        private boolean isReply;
        private int childrenCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<Response> children;

        public static Response from(Comment comment) {
            return Response.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .author(comment.getAuthor())
                    .postId(comment.getPost().getId())
                    .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                    .isReply(comment.isReply())
                    .childrenCount(comment.getChildrenCount())
                    .createdAt(comment.getCreatedAt())
                    .updatedAt(comment.getUpdatedAt())
                    .children(comment.getChildren().stream()
                            .map(Response::from)
                            .collect(Collectors.toList()))
                    .build();
        }

        public static Response fromWithoutChildren(Comment comment) {
            return Response.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .author(comment.getAuthor())
                    .postId(comment.getPost().getId())
                    .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                    .isReply(comment.isReply())
                    .childrenCount(comment.getChildrenCount())
                    .createdAt(comment.getCreatedAt())
                    .updatedAt(comment.getUpdatedAt())
                    .children(new ArrayList<>())
                    .build();
        }
    }
}
