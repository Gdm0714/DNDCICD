package com.example.demo.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.comment.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostEntityIdOrderByCreatedAtAsc(Long postId);

    List<Comment> findByPostEntityIdAndParentIsNullOrderByCreatedAtAsc(Long postId);

    List<Comment> findByParentIdOrderByCreatedAtAsc(Long parentId);

    long countByPostEntityId(Long postId);

    long countByMemberRowId(Long memberRowId);

    List<Comment> findTop5ByPostEntityIdOrderByCreatedAtDesc(Long postId);
}
