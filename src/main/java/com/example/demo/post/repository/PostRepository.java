package com.example.demo.post.repository;

import com.example.demo.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByTitleContainingOrderByCreatedAtDesc(String title, Pageable pageable);

    Page<Post> findByMemberRowIdOrderByCreatedAtDesc(Long memberRowId, Pageable pageable);

    Page<Post> findByTitleContainingOrContentContainingOrderByCreatedAtDesc(
            String title, String content, Pageable pageable);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Post> findTop10ByOrderByViewCountDesc();

    long countByMemberRowId(Long memberRowId);
}
