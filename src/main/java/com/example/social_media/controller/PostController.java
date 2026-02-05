package com.example.social_media.controller;

import com.example.social_media.model.Comment;
import com.example.social_media.model.Post;
import com.example.social_media.model.User;
import com.example.social_media.repository.CommentRepository;
import com.example.social_media.repository.PostRepository;
import com.example.social_media.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {

    @Autowired private PostRepository postRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private UserRepository userRepository;

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

    @PostMapping("/post/{id}/comment")
    public String addComment(@PathVariable Long id, 
                             @RequestParam String content, 
                             HttpSession session) {
        
        // 1. Cek Login
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) return "redirect:/login";

        // 2. Cari User & Post
        User user = userRepository.findByUsername(username); // Pastikan UserRepository di-autowired di sini juga kalau belum
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // 3. Simpan Komentar
        if (content != null && !content.trim().isEmpty()) {
            Comment comment = new Comment();
            comment.setComment(content); // Pastikan di Entity Comment namanya 'content' atau 'comment' (sesuaikan)
            comment.setUser(user);
            comment.setPost(post);
            comment.setCreatedAt(java.time.LocalDateTime.now());
            
            commentRepository.save(comment);

            // 4. UPDATE COMMENT COUNT DI POST (Penting!)
            // Kita tambah manual counternya biar sinkron sama tampilan
            post.setCommentCount(post.getCommentCount() + 1);
            postRepository.save(post);
        }

        // 5. Balik lagi ke halaman detail tadi
        return "redirect:/post/" + id;
    }
}