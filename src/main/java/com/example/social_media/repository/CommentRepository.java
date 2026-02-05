package com.example.social_media.repository;

import com.example.social_media.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    // Cari komen berdasarkan Post ID, DAN Parent-nya harus NULL (Komentar Utama)
    List<Comment> findByPostIdAndParentIsNullOrderByCreatedAtDesc(Long postId);
}