package com.example.social_media.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        // Ambil data user dari session
        String username = (String) session.getAttribute("loggedInUser");

        // Kalau username KOSONG (belum login), kita lempar ke halaman login aja
        if (username == null) {
            return "redirect:/login";
        }

        // Kalau ADA, kirim nama itu ke index.html
        model.addAttribute("username", username);
        return "index";
    }
}