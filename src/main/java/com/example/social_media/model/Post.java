package com.example.social_media.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "posts") // Sambungkan ke tabel 'posts' yang sudah ada
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Di screenshot isinya teks status ("Movie night...", "Listening to jazz...")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "like_count") // Kita ambil jumlah like juga biar keren
    private Integer likeCount = 0;

    @Column(name = "comment_count")
    private Integer commentCount = 0;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    // RELASI: Tabel 'posts' punya kolom 'user_id' yang nyambung ke tabel 'users'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    // Catatan: Kolom 'category_id', 'media_url' kita abaikan dulu biar gak error.
}