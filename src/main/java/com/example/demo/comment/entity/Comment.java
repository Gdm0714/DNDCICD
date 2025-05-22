package com.example.demo.comment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.post.entity.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @Column(name = "commentRowId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "memberRowId", nullable = false)
    private Long memberRowId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postRowId", nullable = false)
    private Post postEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentRowId")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static Comment of(String content, Long memberRowId, Post post, Comment parent) {
        return new Comment(null, content, memberRowId, post, parent, new ArrayList<>(), null, null);
    }

    // 댓글 수정
    public void update(String content) {
        this.content = content;
    }

    // 대댓글인지 확인
    public boolean isReply() {
        return this.parent != null;
    }

    // 자식 댓글 개수
    public int getChildrenCount() {
        return this.children.size();
    }
}
