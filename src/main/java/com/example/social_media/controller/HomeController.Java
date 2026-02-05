package com.example.social_media.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");
        
        // CCTV
        System.out.println("Cek Login: User isinya = " + username);

        // LOGIKA SATPAM
        if (username == null) {
            System.out.println("User kosong, tendang ke login!");
            return "redirect:/login"; 
        }

        model.addAttribute("username", username);
        return "dashboard"; // <--- GANTI JADI INI (sesuai nama file baru)
    }
}