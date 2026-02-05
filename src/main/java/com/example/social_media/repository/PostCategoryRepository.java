package com.example.social_media.repository;

import com.example.social_media.model.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
}