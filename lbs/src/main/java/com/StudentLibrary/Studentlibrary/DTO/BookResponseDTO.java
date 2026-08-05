package com.StudentLibrary.Studentlibrary.DTO;

import com.StudentLibrary.Studentlibrary.Model.Genre;

public class BookResponseDTO {

    private int id;
    private String name;
    private Genre genre;
    private boolean available;

    private Integer authorId;
    private String authorName;

    public BookResponseDTO() {
    }

    public BookResponseDTO(
            int id,
            String name,
            Genre genre,
            boolean available,
            Integer authorId,
            String authorName
    ) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.available = available;
        this.authorId = authorId;
        this.authorName = authorName;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}