package com.example.social_media;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")  // Ini sama kayak Route::get('/', ...)
    public String home() {
        return "index"; // Ini artinya: "Tampilkan file index.html"
    }
}