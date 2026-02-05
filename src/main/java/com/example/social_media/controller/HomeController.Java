package com.example.social_media.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        // 1. Cek isi kantong (Session)
        String username = (String) session.getAttribute("loggedInUser");

        // 2. LOGIKA SATPAM IS BACK! 👮‍♂️
        // Kalau username kosong (belum login), TENDANG ke halaman login
        if (username == null) {
            return "redirect:/login"; 
        }

        // 3. Kalau lolos (sudah login), baru boleh masuk dashboard
        model.addAttribute("username", username);
        return "dashboard"; 
    }
}