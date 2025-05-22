
package com.example.demo.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

import com.example.demo.post.entity.Post;

public class PostDto {

    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
        private String title;

        @NotBlank(message = "내용은 필수입니다.")
        private String content;

        @NotBlank(message = "작성자는 필수입니다.")
        @Size(max = 50, message = "작성자명은 50자 이하여야 합니다.")
        private String author;

        @Builder
        public CreateRequest(String title, String content, String author) {
            this.title = title;
            this.content = content;
            this.author = author;
        }

        public Post toEntity() {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .author(author)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
        private String title;

        @NotBlank(message = "내용은 필수입니다.")
        private String content;

        @Builder
        public UpdateRequest(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private String author;
        private Long viewCount;
        private int commentCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static Response from(Post post) {
            return Response.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .author(post.getAuthor())
                    .commentCount(post.getCommentCount())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class ListResponse {
        private Long id;
        private String title;
        private String author;
        private Long viewCount;
        private int commentCount;
        private LocalDateTime createdAt;

        public static ListResponse from(Post post) {
            return ListResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .author(post.getAuthor())
                    .commentCount(post.getCommentCount())
                    .createdAt(post.getCreatedAt())
                    .build();
        }
    }
}
