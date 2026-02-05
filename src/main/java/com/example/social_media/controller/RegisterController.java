package com.example.social_media.controller;

import jakarta.servlet.http.HttpSession; // Jangan lupa import ini
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String showRegisterPage(HttpSession session) { // Tambahkan parameter session
        // CEK: Kalau user SUDAH login, ngapain daftar lagi? Tendang ke Dashboard (/)
        if (session.getAttribute("loggedInUser") != null) {
            return "redirect:/";
        }

        return "register";
    }
}