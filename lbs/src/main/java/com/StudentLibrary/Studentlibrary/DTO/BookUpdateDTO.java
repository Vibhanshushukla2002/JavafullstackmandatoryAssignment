package com.StudentLibrary.Studentlibrary.DTO;

import com.StudentLibrary.Studentlibrary.Model.Genre;

public class BookUpdateDTO {

    private int id;
    private String name;
    private Genre genre;
    private Integer authorId;

    public BookUpdateDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
}