package com.StudentLibrary.Studentlibrary.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.StudentLibrary.Studentlibrary.Model.Book;
import com.StudentLibrary.Studentlibrary.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import com.StudentLibrary.Studentlibrary.DTO.BookResponseDTO;
import com.StudentLibrary.Studentlibrary.DTO.BookUpdateDTO;



import javax.validation.Valid;
import java.util.List;
import com.StudentLibrary.Studentlibrary.DTO.BookRequestDTO;
@CrossOrigin(origins = {"http://localhost:5174"})
@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/books/count")
    public ResponseEntity<Long> getBooksCount() {

        return new ResponseEntity<>(
                bookService.getBooksCount(),
                HttpStatus.OK
        );

    }

    @PostMapping("/createBook")
    public ResponseEntity<String> createBook(@RequestBody BookRequestDTO request){

        bookService.createBook(request);

        return new ResponseEntity<>(
                "Book Added Successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity getAllBooks() {

        return new ResponseEntity(
                bookService.getAllBooks(),
                HttpStatus.OK
        );
    }
    @GetMapping("/getBooks")
    public ResponseEntity getBooks(@RequestParam(value = "genre",required = false) String genre,
                                   @RequestParam(value = "available",required = false,defaultValue = "false") boolean available,
                                   @RequestParam(value = "author",required = false) String author){

        List<Book> bookList=bookService.getBooks(genre,available,author);
        return new ResponseEntity(bookList,HttpStatus.OK);




    }



    @GetMapping("/books/issuedCount")
    public ResponseEntity<Long> getIssuedBooksCount() {

        return new ResponseEntity<>(
                bookService.getIssuedBooksCount(),
                HttpStatus.OK
        );
    }
    @PutMapping("/updateBook")
    public ResponseEntity<String> updateBook(
            @RequestBody BookUpdateDTO request
    ) {

        bookService.updateBook(request);

        return new ResponseEntity<>(
                "Book Updated Successfully",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<String> deleteBook(@RequestParam Integer id) {

        bookService.deleteBook(id);

        return new ResponseEntity<>("Book Deleted Successfully", HttpStatus.OK);

    }

    @GetMapping("/searchBook")
    public ResponseEntity<List<Book>> searchBook(@RequestParam String name) {

        return new ResponseEntity<>(
                bookService.searchBook(name),
                HttpStatus.OK
        );

    }
//    @GetMapping("/books/page")
@GetMapping("/getBooksByPage")
public ResponseEntity<Page<BookResponseDTO>> getBooksByPage(
        @RequestParam int page,
        @RequestParam int size
) {

    return ResponseEntity.ok(
            bookService.getBooksByPage(page, size)
    );
}








}
