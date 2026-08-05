package com.StudentLibrary.Studentlibrary.Controllers;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.StudentLibrary.Studentlibrary.Model.Author;
import com.StudentLibrary.Studentlibrary.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "http://localhost:5174")
@RestController
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/createAuthor")
    public ResponseEntity createAuthor(@Valid @RequestBody Author author){
        authorService.createAuthor(author);
        return new ResponseEntity("Author created", HttpStatus.CREATED);
    }
    @PutMapping("/updateAuthor")
    public ResponseEntity updateAuthor( @Valid @RequestBody Author author){
        authorService.updateAuthor(author);
        return new ResponseEntity("Auhtor upadted!!",HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/deleteAuthor")
    public ResponseEntity deleteAuthor(@RequestParam("id") int id){
        authorService.deleteAuthor(id);
        return new ResponseEntity("Author deleted!!",HttpStatus.ACCEPTED);

    }
    @GetMapping("/getAuthors")
    public ResponseEntity getAuthors() {

        return new ResponseEntity(
                authorService.getAuthors(),
                HttpStatus.OK
        );

    }

    @GetMapping("/authors/count")
    public ResponseEntity<Long> getAuthorsCount() {
        return new ResponseEntity<>(
                authorService.getAuthorsCount(),
                HttpStatus.OK
        );
    }
    @GetMapping("/searchAuthor")
    public ResponseEntity<List<Author>> searchAuthor(
            @RequestParam String name
    ) {

        return ResponseEntity.ok(
                authorService.searchAuthor(name)
        );

    }

    @GetMapping("/getAuthorsByPage")
    public ResponseEntity<Page<Author>> getAuthorsByPage(
            @RequestParam int page,
            @RequestParam int size
    ) {

        return ResponseEntity.ok(
                authorService.getAuthorsByPage(page, size)
        );

    }

}
