package com.example.social_media.controller;

import com.example.social_media.model.User;
import com.example.social_media.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository; // Panggil alat database

    @GetMapping("/register")
    public String showRegisterPage(HttpSession session) {
        // Reverse Auth: Kalau udah login, jangan kasih daftar lagi
        if (session.getAttribute("loggedInUser") != null) {
            return "redirect:/";
        }
        return "register";
    }

    // LOGIKA PENDAFTARAN (BARU)
    @PostMapping("/register")
    public String processRegister(@RequestParam String fullName,
                                  @RequestParam String username,
                                  @RequestParam String email,
                                  @RequestParam String password,
                                  Model model) {

        // 1. Cek apakah email sudah pernah dipakai?
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            // Kalau ada, kirim pesan error dan balik ke form register
            model.addAttribute("error", "Waduh, email ini sudah terdaftar!");
            return "register";
        }

        // 2. Kalau aman, buat objek User baru
        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password); // Simpan password (nanti kita enkripsi kalau sudah pro)

        // 3. Simpan ke Database MySQL
        userRepository.save(newUser);

        // 4. Sukses? Lempar ke halaman login biar dia login manual
        return "redirect:/login";
    }
}