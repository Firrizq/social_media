package com.example.social_media.model;

import jakarta.persistence.*;
import lombok.Data;

@Data // 1. Ini Lombok: Otomatis bikinin Getter, Setter, & toString (Gak usah ketik manual)
@Entity // 2. Menandakan class ini adalah tabel database
@Table(name = "users") // 3. PENTING: Sesuaikan dengan nama tabel di MySQL kamu (biasanya 'users')
public class User {

    @Id // Menandakan ini Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    private Long id;

    // Nama variabel ini harus camelCase, nanti otomatis nyambung ke field snake_case di MySQL
    // Contoh: fullName -> akan mencari kolom 'full_name' atau 'fullname' (tergantung setting)
    // Biar aman, kita mapping manual kalau nama kolomnya beda.
    
    @Column(name = "full_name") // Sesuaikan nama kolom di tabel MySQL kamu
    private String fullName;

    @Column(unique = true) // Username gak boleh kembar
    private String username;

    @Column(unique = true) // Email juga gak boleh kembar
    private String email;

    private String password;
}