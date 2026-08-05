package com.StudentLibrary.Studentlibrary.Controllers;

import com.StudentLibrary.Studentlibrary.Services.GoogleBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/google-books")
@CrossOrigin(origins = "http://localhost:5173")

public class GoogleBooksController {

    @Autowired
    private GoogleBooksService googleBooksService;

    @GetMapping("/search")
    public String searchBook(@RequestParam String query) {

        return googleBooksService.searchBook(query);
    }
}
