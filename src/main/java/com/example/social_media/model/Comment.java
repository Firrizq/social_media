package com.example.social_media.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "like_count")
    private Integer likeCount = 0;

    // RELASI KE USER (Siapa yang komen)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // RELASI KE POST (Komen di post mana)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // --- LOGIKA REPLY (PARENT - CHILD) ---
    
    // 1. Parent: Menunjuk ke komentar yang dibalas (Bisa NULL kalau ini komen utama)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @ToString.Exclude // Biar gak error looping saat diprint
    private Comment parent;

    // 2. Replies: Daftar balasan untuk komentar ini
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OrderBy("createdAt ASC") // Balasan urut dari yang terlama
    private List<Comment> replies = new ArrayList<>();
}