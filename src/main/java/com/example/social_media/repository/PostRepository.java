package com.example.social_media.repository;

import com.example.social_media.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    
    // Ambil semua post, urutkan dari yang paling baru dibuat
    List<Post> findAllByOrderByCreatedAtDesc();
}