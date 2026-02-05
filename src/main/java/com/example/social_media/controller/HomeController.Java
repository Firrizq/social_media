package com.example.social_media.controller;

import com.example.social_media.model.Post;
import com.example.social_media.model.User;
import com.example.social_media.repository.PostRepository;
import com.example.social_media.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");

        // 1. Cek Login (Satpam)
        if (username == null) {
            return "redirect:/login"; 
        }

        // 2. Ambil data User lengkap (biar bisa nampilin nama asli/foto profil nanti)
        User currentUser = userRepository.findByEmail(session.getAttribute("loggedInUser").toString()); 
        // Note: Asumsi session nyimpen username/email. Kalau nyimpen username, ganti findByUsername.
        // Tapi biar aman, kita pakai variable 'username' yang ada aja.
        
        // 3. AMBIL SEMUA POSTINGAN DARI DATABASE
        List<Post> listPost = postRepository.findAllByOrderByCreatedAtDesc();

        // 4. Kirim ke HTML
        model.addAttribute("username", username);
        model.addAttribute("posts", listPost); // Kirim daftar post
        
        return "dashboard"; 
    }

    @PostMapping("/post")
    public String createPost(@RequestParam String content, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByUsername(username);
        
        if (content != null && !content.trim().isEmpty()) {
            Post newPost = new Post();
            newPost.setContent(content);
            newPost.setUser(user);
            newPost.setLikeCount(0);
            newPost.setCategoryId(1L); 
            
            postRepository.save(newPost);
        }

        return "redirect:/";
    }
}