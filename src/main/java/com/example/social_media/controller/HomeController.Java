package com.example.social_media.controller;

import com.example.social_media.model.Post;
import com.example.social_media.model.PostCategory;
import com.example.social_media.model.User;
import com.example.social_media.repository.PostRepository;
import com.example.social_media.repository.UserRepository;
import com.example.social_media.repository.PostCategoryRepository;
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

    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PostCategoryRepository categoryRepository; // <--- INJECT REPO BARU

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) return "redirect:/login";

        // Ambil User
        User currentUser = userRepository.findByUsername(username); 

        // Ambil Semua Post
        List<Post> listPost = postRepository.findAllByOrderByCreatedAtDesc();
        
        // AMBIL DAFTAR KATEGORI (Buat Dropdown)
        List<PostCategory> categories = categoryRepository.findAll(); // <--- AMBIL DATA

        model.addAttribute("username", username);
        model.addAttribute("posts", listPost);
        model.addAttribute("categories", categories); // <--- KIRIM KE HTML
        
        return "dashboard"; 
    }

    @PostMapping("/post")
    public String createPost(@RequestParam String content, 
                             @RequestParam Long categoryId, // <--- TERIMA INPUT KATEGORI
                             HttpSession session) {
        
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) return "redirect:/login";

        User user = userRepository.findByUsername(username);
        
        if (content != null && !content.trim().isEmpty()) {
            Post newPost = new Post();
            newPost.setContent(content);
            newPost.setUser(user);
            newPost.setLikeCount(0);
            
            // PAKAI KATEGORI PILIHAN USER
            newPost.setCategoryId(categoryId); 
            
            postRepository.save(newPost);
        }

        return "redirect:/";
    }
}