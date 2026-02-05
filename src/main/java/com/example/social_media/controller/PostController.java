package com.example.social_media.controller;

import com.example.social_media.model.Comment;
import com.example.social_media.model.Post;
import com.example.social_media.repository.CommentRepository;
import com.example.social_media.repository.PostRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PostController {

    @Autowired private PostRepository postRepository;
    @Autowired private CommentRepository commentRepository;

    @GetMapping("/post/{id}")
    public String postDetail(@PathVariable Long id, HttpSession session, Model model) {
        // 1. Cek Login
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) return "redirect:/login";

        // 2. Ambil Data Postingan (Kalau gak ada, error atau redirect)
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post tidak ditemukan"));

        // 3. Ambil Komentar (Hanya induknya saja)
        List<Comment> rootComments = commentRepository.findByPostIdAndParentIsNullOrderByCreatedAtDesc(id);

        model.addAttribute("username", username);
        model.addAttribute("post", post);
        model.addAttribute("comments", rootComments);

        return "post_detail";
    }
}