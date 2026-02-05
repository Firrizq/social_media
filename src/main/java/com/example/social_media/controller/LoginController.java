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
public class LoginController {

    @Autowired
    private UserRepository userRepository; // Panggil alat repository

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, 
                               @RequestParam String password, 
                               Model model, 
                               HttpSession session) {
        
        // 1. Cari user di database berdasarkan email
        User user = userRepository.findByEmail(email);

        // 2. Cek apakah user ketemu DAN passwordnya cocok
        if (user != null && user.getPassword().equals(password)) {
            // SUKSES LOGIN
            // Simpan data user (username) ke Session biar bisa dibawa ke halaman lain
            session.setAttribute("loggedInUser", user.getUsername());
            
            // Redirect ke halaman utama (/)
            return "redirect:/";
        } else {
            // GAGAL LOGIN
            model.addAttribute("error", "The Credentials doesn't match");
            return "login"; // Balik lagi ke halaman login
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 1. HAPUS SEMUA ISI KANTONG (SESSION)
        session.invalidate(); 
        
        // 2. Tendang balik ke halaman login
        return "redirect:/login";
    }
}