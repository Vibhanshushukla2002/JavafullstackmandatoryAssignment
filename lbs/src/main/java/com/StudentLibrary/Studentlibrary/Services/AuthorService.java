package com.StudentLibrary.Studentlibrary.Services;

import com.StudentLibrary.Studentlibrary.Model.Author;
import com.StudentLibrary.Studentlibrary.Repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;


    public void createAuthor(Author author){
        authorRepository.save(author);

    }
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }
    public void updateAuthor(Author author){
        authorRepository.updateAuthorDetails(author);
    }
    public void deleteAuthor(int id ){
        authorRepository.deleteCustom(id);
    }
    public long getAuthorsCount() {
        return authorRepository.count();
    }
    public List<Author> searchAuthor(String name) {

        return authorRepository.findByNameContainingIgnoreCase(name);

    }

    public Page<Author> getAuthorsByPage(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return authorRepository.findAll(pageable);

    }
}
