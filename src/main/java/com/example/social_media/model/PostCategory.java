package com.example.social_media.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "post_categories") // Sesuai nama tabel di screenshot kamu
public class PostCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Technology, Music, dll

    // Kolom created_at, dll kita abaikan dulu karena cuma butuh namanya buat dropdown
}