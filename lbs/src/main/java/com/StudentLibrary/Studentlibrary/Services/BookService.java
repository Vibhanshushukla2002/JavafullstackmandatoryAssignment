package com.StudentLibrary.Studentlibrary.Services;

import com.StudentLibrary.Studentlibrary.Model.Book;
import com.StudentLibrary.Studentlibrary.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.StudentLibrary.Studentlibrary.DTO.BookRequestDTO;
import com.StudentLibrary.Studentlibrary.Model.Author;
import com.StudentLibrary.Studentlibrary.Repositories.AuthorRepository;
import com.StudentLibrary.Studentlibrary.DTO.BookResponseDTO;
import com.StudentLibrary.Studentlibrary.DTO.BookUpdateDTO;

import java.util.Optional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    public void createBook(BookRequestDTO request) {

        Optional<Author> optionalAuthor =
                authorRepository.findByNameIgnoreCase(request.getAuthorName());

        Author author;

        if (optionalAuthor.isPresent()) {

            author = optionalAuthor.get();

        } else {

            author = new Author();

            author.setName(request.getAuthorName());

            // Default values
            author.setEmail(request.getAuthorName().toLowerCase().replace(" ", "") + "@library.com");
            author.setAge(0);
            author.setCountry("Unknown");

            author = authorRepository.save(author);
        }

        Book book = new Book();

        book.setName(request.getName());
        book.setGenre(request.getGenre());
        book.setAuthor(author);

        bookRepository.save(book);
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    //Either giving you all the available books
    //OR giving you all the non-available books
    public List<Book> getBooks(String genre, boolean isAvailable,String author){

        if (genre!=null&&author!=null){
            return bookRepository.findBooksByGenre_Author(genre,author,isAvailable);
        }
        else if (genre!=null){
            return bookRepository.findBooksByGenre(genre,isAvailable);
        }
        else if (author!=null){
            return bookRepository.findBooksByAuthor(author,isAvailable);
        }
        return bookRepository.findBooksByAvailability(isAvailable);







    }
    public long getBooksCount() {
        return bookRepository.count();
    }
    public long getIssuedBooksCount() {
        return bookRepository.countIssuedBooks();
    }


    public void updateBook(BookUpdateDTO request) {

        Book existingBook = bookRepository.findById(request.getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Book not found with id: " + request.getId()
                        )
                );

        existingBook.setName(request.getName());
        existingBook.setGenre(request.getGenre());

        if (request.getAuthorId() != null) {

            Author author = authorRepository.findById(request.getAuthorId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Author not found with id: "
                                            + request.getAuthorId()
                            )
                    );

            existingBook.setAuthor(author);
        }

        bookRepository.save(existingBook);
    }

    public void deleteBook(Integer id) {

        bookRepository.deleteById(id);

    }

    public List<Book> searchBook(String name) {

        return bookRepository.findByNameContainingIgnoreCase(name);

    }
    public Page<BookResponseDTO> getBooksByPage(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Book> books = bookRepository.findAll(pageable);

        return books.map(book -> {

            Integer authorId = null;
            String authorName = null;

            if (book.getAuthor() != null) {
                authorId = book.getAuthor().getId();
                authorName = book.getAuthor().getName();
            }

            return new BookResponseDTO(
                    book.getId(),
                    book.getName(),
                    book.getGenre(),
                    book.isAvailable(),
                    authorId,
                    authorName
            );
        });
    }

}
