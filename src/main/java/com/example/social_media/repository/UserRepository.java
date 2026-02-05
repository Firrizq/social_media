package com.example.social_media.repository;

import com.example.social_media.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// Ini ajaib! Cuma extends JpaRepository, kita langsung punya fitur findByEmail, save, dll.
public interface UserRepository extends JpaRepository<User, Long> {
    // Kita tambahkan satu custom method buat cari user via email
    User findByEmail(String email);

    User findByUsername(String username);
}